package com.ninjacart.task_mgmt_service.Service;
import com.google.common.collect.Lists;
import com.ninjacart.task_mgmt_service.Exception.BadRequestException;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.Exception.DataNotFoundException;
import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceRepository;
import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceTaskRepository;
import com.ninjacart.task_mgmt_service.entity.*;
import com.ninjacart.task_mgmt_service.model.ProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.model.*;
import com.ninjacart.task_mgmt_service.model.enums.CommonEnum;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.ninjacart.task_mgmt_service.Exception.LockAcquisitionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service

public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private ProcessServiceImpl processServiceImpl;
    @Autowired
    private ProcessInstanceTaskService processInstanceTaskService;
    @Autowired
    private ProcessInstanceVariableService processInstanceVariableService;

    @Autowired
    @Lazy
    private ProcessInstanceServiceHelper processInstanceServiceHelper;
    @Autowired
    private ProcessURLConfiguration processURLConfiguration;
    private final static String CMT_VIOLATION_DOCUMENTS_FOLDER = "cmt-violation";

    @Override
    public List<ProcessInstanceDTO> createProcess(User principal, HttpServletRequest request, List<WorkFlowPayLoad> workFlowPayLoads) throws Exception {
        log.info("Request received with payload: {}", workFlowPayLoads);

        if (workFlowPayLoads == null || workFlowPayLoads.size() == 0) {
            // Logging incoming request details
            log.info("Received request with {} workflow payloads.", workFlowPayLoads.size());
            throw new BadRequestException("Invalid Request");
        }

        try {
            // Logging incoming request details
            log.info("Received request with {} workflow payloads.", workFlowPayLoads.size());

            processInstanceServiceHelper.addCacheForProcessAndReference(workFlowPayLoads);

            List<WorkFlowPayLoad> autoCompleteWorkFlowPlayLoads = workFlowPayLoads.stream()
                    .filter(WorkFlowPayLoad::isAutoComplete)
                    .collect(Collectors.toList());

            List<Integer> autoCloseProcessIds = processURLConfiguration.getIntegerProperty("crm.auto.close.process.ids",
                    "87,88,89,90,91,92,93,94,95,146,147,148,149,150,151,152");

            // Check if any process should be auto-closed based on config
            for (WorkFlowPayLoad payload : workFlowPayLoads) {
                if (autoCloseProcessIds.stream().anyMatch(x -> x == payload.getProcessId())) {
                    log.info("Found process id to auto close through config {} ", payload);
                    autoCompleteWorkFlowPlayLoads.add(payload);
                }
            }

            autoCompleteWorkFlowPlayLoads = autoCompleteWorkFlowPlayLoads.stream()
                    .distinct()
                    .collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(autoCompleteWorkFlowPlayLoads)) {
                return this.createAndCloseProcess(principal, request, autoCompleteWorkFlowPlayLoads);
            }

            // Proceed to create process instances for non-auto-complete payloads
            List<ProcessInstanceTask> processInstanceTaskList = createProcessInstanceList(request, principal, workFlowPayLoads);
            List<ProcessInstanceDTO> processInstanceDTOList = new ArrayList<>();

            processInstanceTaskList.forEach(each -> {
                ProcessInstanceDTO processInstanceDTO = ProcessInstanceDTO.builder()
                        .processInstanceTaskId(each.getId())
                        .processInstanceId(each.getProcessInstanceId())
                        .build();
                processInstanceDTOList.add(processInstanceDTO);
            });

            return processInstanceDTOList;

        } catch (LockAcquisitionException lae) {
            throw lae;
        } finally {
            processInstanceServiceHelper.removeCacheForProcessAndReference(workFlowPayLoads);
        }
    }

    @SneakyThrows
    private List<ProcessInstanceDTO> createAndCloseProcess(User principal, HttpServletRequest request, List<WorkFlowPayLoad> autoCompleteWorkFlowPlayLoads) {
        List<ProcessInstanceTask> processInstanceTaskList = this.constructProcessInstanceTasks(autoCompleteWorkFlowPlayLoads);

        if (CollectionUtils.isEmpty(processInstanceTaskList)) {
            processInstanceTaskList = this.createProcessInstanceList(request, principal, autoCompleteWorkFlowPlayLoads);
        }

        log.info("Attempting to auto complete workflow payloads: {}", autoCompleteWorkFlowPlayLoads);

        // Commented out convoxDTO logic for now
        // this.autoComplete(principal, this.constructAutoCompleteDTO(autoCompleteWorkFlowPlayLoads));

        List<ProcessInstanceDTO> processInstanceDTOList = new ArrayList<>();
        processInstanceTaskList.forEach(each -> {
            ProcessInstanceDTO processInstanceDTO = ProcessInstanceDTO.builder()
                    .processInstanceTaskId(each.getId())
                    .processInstanceId(each.getProcessInstanceId())
                    .build();
            processInstanceDTOList.add(processInstanceDTO);
        });

        return processInstanceDTOList;
    }
    @Override
    public List<ProcessInstance> getProcessInstances(List<Integer> processIds, List<ProcessInstanceStatus> statusList, List<String> references, Integer limit, Integer offset, String fromDate, String toDate) {
        return processInstanceRepository.getProcessInstances(processIds, statusList, references, limit, offset, fromDate, toDate, null);
    }

    private List<ProcessInstanceTask> constructProcessInstanceTasks(List<WorkFlowPayLoad> autoCompleteWorkFlowPlayLoads) {
        List<ProcessInstanceStatus> statuses = Lists.newArrayList(ProcessInstanceStatus.PENDING, ProcessInstanceStatus.IN_PROGRESS);
        int processId = autoCompleteWorkFlowPlayLoads.iterator().next().getProcessId();
        List<String> actualReferences = processInstanceServiceHelper.getReferences(autoCompleteWorkFlowPlayLoads);
        List<String> referenceTypeList = autoCompleteWorkFlowPlayLoads.stream()
                .map(WorkFlowPayLoad::getReferenceType)
                .collect(Collectors.toList());

        List<ProcessInstance> existingProcessInstances = processInstanceRepository.getProcessInstances(Collections.singletonList(processId), statuses,
                actualReferences, null, null, null, null, referenceTypeList);


        if (CollectionUtils.isEmpty(existingProcessInstances)) {
            return Collections.emptyList();
        }

        List<Integer> processInstanceIds = existingProcessInstances.stream()
                .map(ProcessInstance::getId)
                .collect(Collectors.toList());

        return processInstanceTaskService.getProcessInstanceTasksByProcessInstanceIds(processInstanceIds);
    }

    @SneakyThrows
    private AutoComplete constructAutoCompleteDTO(List<WorkFlowPayLoad> autoCompleteWorkFlowPlayLoads) {
        Integer processId = autoCompleteWorkFlowPlayLoads.stream()
                .map(WorkFlowPayLoad::getProcessId)
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Process ID is missing"));

        List<String> references = autoCompleteWorkFlowPlayLoads.stream()
                .map(WorkFlowPayLoad::getReference)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());

        AutoComplete autoComplete = new AutoComplete();
        autoComplete.setProcessId(processId);
        autoComplete.setReferences(references);

        return autoComplete;
    }

    public void createProcessInstance(User user, List<WorkFlowPayLoad> workFlowPayLoadList) {
        ProcessInstance processInstance = new ProcessInstance();
        processInstanceRepository.save(processInstance);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ProcessInstanceTask> createProcessInstanceList(HttpServletRequest request, User user, List<WorkFlowPayLoad> workFlowPayLoadList)
            throws Exception {
        if (workFlowPayLoadList == null || workFlowPayLoadList.size() == 0) {
            throw new DataNotFoundException("WorkFlowPayLoadList should not be empty");
        }

        log.info("Starting to create process instances with {} workflow payloads.", workFlowPayLoadList.size());

        int processId = workFlowPayLoadList.get(0).getProcessId();
        Integer type = workFlowPayLoadList.get(0).getType();
        boolean ticketCreateValidationFlag = true;
        boolean disableErrorIfTicketExist = workFlowPayLoadList.get(0).isDisableErrorIfTicketExist();

        processInstanceServiceHelper.validateProcessCreationForStakeholder(workFlowPayLoadList);

        List<String> actualReferences = processInstanceServiceHelper.getReferences(workFlowPayLoadList);
        List<String> referenceTypeList = null;
        if (!CollectionUtils.isEmpty(workFlowPayLoadList)) {
            if (Objects.nonNull(workFlowPayLoadList.get(0).getReferenceType()))
                referenceTypeList = Collections.singletonList(workFlowPayLoadList.get(0).getReferenceType());
        }

        List<ProcessInstanceStatus> statuses = new ArrayList<>();
        statuses.add(ProcessInstanceStatus.PENDING);
        statuses.add(ProcessInstanceStatus.IN_PROGRESS);
        log.info("till here it is going");
        List<ProcessInstance> existingProcessInstances = processInstanceRepository.
                getProcessInstances(Arrays.asList(processId), statuses, actualReferences, null, null, null, null, referenceTypeList);

        // Removed ProcessHandler logic and placed custom logic
        ConvoxDTO convoxDTO = null;

        if (workFlowPayLoadList == null || workFlowPayLoadList.isEmpty()) {
            if (processId == 11 || disableErrorIfTicketExist) {
                log.info("No existing ticket found, creating new ticket for process id: {}", processId);
                return new ArrayList<>();
            } else {
                throw new BadRequestException("Ticket is already in progress. Please wait for some time.");
            }
        }

        List<ProcessInstanceTask> processInstanceTaskList = new ArrayList<>();
        for (WorkFlowPayLoad workFlowPayLoad : workFlowPayLoadList) {
            ProcessInstance processInstance = processCreateWorkFlow(user.getId(), workFlowPayLoad);
            processInstanceVariableService.createProcessInstanceVariables(user.getId(), processInstance.getProcessId(), processInstance.getId(), workFlowPayLoad.getWorkflowVariables());

            int assignToAgentId = getAssignToAgentId(request, workFlowPayLoad);
            ProcessInstanceTask processInstanceTask = processInstanceTaskService.createProcessInstanceTaskForProcessInstance(user.getId(),
                    workFlowPayLoad, processInstance.getId(), workFlowPayLoad.getProcessId(), 1, assignToAgentId, workFlowPayLoad.getCreationDate());

            processInstanceTaskList.add(processInstanceTask);
        }

        // After creation, execute auto assignment
        log.info("Created {} process instance tasks.", processInstanceTaskList.size());

        return processInstanceTaskList;
    }

    private int getAssignToAgentId(HttpServletRequest request, WorkFlowPayLoad workFlowPayLoad) throws Exception {
        if (Objects.nonNull(workFlowPayLoad.getActionObject())
                && Objects.nonNull(workFlowPayLoad.getActionObject().get("assignedTo"))) {
            return Integer.parseInt(workFlowPayLoad.getActionObject().get("assignedTo").toString());
        }
        return CommonEnum.SYSTEM_USER; // Default system user if no agent is assigned
    }


    private ProcessInstance processCreateWorkFlow(int userId, WorkFlowPayLoad workFlowPayLoad) throws CyborgException {
        ProcessInstance processInstance = new ProcessInstance();
        if (workFlowPayLoad.getProcessId() == 171 && workFlowPayLoad.getProcessInstanceId() != null) {
            Optional<ProcessInstance> optionalProcessInstance = processInstanceRepository.findById(workFlowPayLoad.getProcessInstanceId());
            if (optionalProcessInstance.isPresent()) {
                processInstance = optionalProcessInstance.get();
            }
        }
        processInstance.setProcessId(workFlowPayLoad.getProcessId());
        processInstance.setReference(workFlowPayLoad.getReference());
        processInstance.setStatus(ProcessInstanceStatus.PENDING);
        if (Objects.nonNull(workFlowPayLoad.getPiStatus())) {
            processInstance.setStatus(workFlowPayLoad.getPiStatus());
        }
        processInstance.setActionObject(CommonUtils.getStringFromObject(workFlowPayLoad.getActionObject()));
        processInstance.setDeleted((byte) 0);
        String languagePreference = workFlowPayLoad.getLanguagePreference();
        if (languagePreference == null || languagePreference.isEmpty()) {
            log.info("SETTING LANGUAGE MANUALLY DURING TICKET CREATION");
            languagePreference = "3";
        }
        processInstance.setLanguage(languagePreference);
        processInstance.setReferenceType(workFlowPayLoad.getReferenceType());
        processInstanceRepository.save(processInstance);
        return processInstance;
    }


    @Override
    public List<StakeholderProcessInstanceDTO> getActiveProcessForStakeholder(int stakeholderId, String domainName, List<Integer> ignoreProcessIds, Integer thresholdMinutes) {
        return processInstanceRepository.getActiveProcessForStakeholder(stakeholderId, domainName, ignoreProcessIds, thresholdMinutes);
    }

    @Override
    public void updateProcessInstances(List<ProcessInstance> processInstances) {
        processInstanceRepository.saveAll(processInstances);
    }

    @Override
    public List<ProcessInstance> findOpenPIsByReferenceAndProcess(String reference, Integer processId) throws Exception {
        if (StringUtils.isEmpty(reference) || Objects.isNull(processId)) {
            throw new BadRequestException("Mandatory params are missing");
        }
        return processInstanceRepository.fetchOpenPIsByReferenceAndProcess(reference, processId);
    }

    @Override
    public List<ProcessInstanceDTO> findOpenPIByReferenceAndExternalReferenceForOndc(String reference, String externalReference) throws Exception {
        return processInstanceRepository.findOpenPIByReferenceAndExternalReferenceForOndc(reference, externalReference);
    }
}
