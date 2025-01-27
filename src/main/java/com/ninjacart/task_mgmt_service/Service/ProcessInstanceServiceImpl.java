package com.ninjacart.task_mgmt_service.Service;
import com.google.common.collect.Lists;
import com.ninjacart.task_mgmt_service.Exception.BadRequestException;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.Exception.DataNotFoundException;
import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceHandler;
import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceRepository;
import com.ninjacart.task_mgmt_service.entity.*;
import com.ninjacart.task_mgmt_service.model.ProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.model.*;
import com.ninjacart.task_mgmt_service.model.enums.*;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.ninjacart.task_mgmt_service.Exception.LockAcquisitionException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@Component
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;
    @Autowired
    private AutoAssignTaskHelper autoAssignTaskHelper;

    @Autowired
    private ProcessInstanceTaskService processInstanceTaskService;

    @Autowired
    private ProcessServiceImpl processServiceImpl;

    @Autowired
    private MapperUtils mapperUtils;
    @Autowired
    private ProcessInstanceServiceHelper processInstanceServiceHelper;
    @Autowired
    private ProcessURLConfiguration processURLConfiguration;
    @Autowired
    private ProcessInstanceHandlerFactory processInstanceHandlerFactory;
    @Autowired
    private ProcessInstanceVariableService processInstanceVariableService;
    @Autowired
    private TeamProcessTaskService teamProcessTaskService;


    private final static String CMT_VIOLATION_DOCUMENTS_FOLDER = "cmt-violation";

    @Override
    public List<ProcessInstanceDTO> createProcess(User principal, HttpServletRequest request, List<WorkFlowPayLoad> workFlowPayLoads) throws Exception {
        if (workFlowPayLoads == null || workFlowPayLoads.size() == 0) {
            throw new BadRequestException("Invalid Request");
        }
        try {
            // Add cache for process and reference
            processInstanceServiceHelper.addCacheForProcessAndReference(workFlowPayLoads);

            // Filtering AutoComplete WorkFlowPayLoads
            List<WorkFlowPayLoad> autoCompleteWorkFlowPlayLoads = workFlowPayLoads.stream()
                    .filter(WorkFlowPayLoad::isAutoComplete)
                    .collect(Collectors.toList());

            // Configuring auto close processes
            List<Integer> autoCloseProcessIds = processURLConfiguration.getIntegerProperty(
                    "crm.auto.close.process.ids",
                    "87,88,89,90,91,92,93,94,95,146,147,148,149,150,151,152"
            );
            for (WorkFlowPayLoad payload : workFlowPayLoads) {
                if (autoCloseProcessIds.stream().anyMatch(x -> x == payload.getProcessId())) {
                    autoCompleteWorkFlowPlayLoads.add(payload);
                }
            }

            // Removing duplicates from the list
            autoCompleteWorkFlowPlayLoads = autoCompleteWorkFlowPlayLoads.stream()
                    .distinct()
                    .collect(Collectors.toList());

            // Handling auto complete scenario
            if (!CollectionUtils.isEmpty(autoCompleteWorkFlowPlayLoads)) {
                log.info("Auto-complete WorkFlowPayLoads found. Processing tasks: " + autoCompleteWorkFlowPlayLoads.size() + " tasks to process.");
            }

            // Creating Process Instance List from WorkFlowPayload
            List<ProcessInstanceTask> processInstanceTaskList = createProcessInstanceList(request, principal, workFlowPayLoads);

            // Building ProcessInstanceDTOs
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
            // Remove cache for process and reference
            processInstanceServiceHelper.removeCacheForProcessAndReference(workFlowPayLoads);
        }
    }

    private int generateProcessInstanceId(WorkFlowPayLoad payload) {
        // Assuming processId and creationDate can be used to generate a unique ID
        int baseId = payload.getProcessId(); // Using processId
        long timestamp = payload.getCreationDate().getTime(); // Using creationDate as part of the unique ID
        return baseId * 100000 + (int) (timestamp % 100000); // Creating a unique ProcessInstanceId
    }

    private int generateTaskId(WorkFlowPayLoad payload) {
        int baseId = payload.getProcessId();  // Using processId as the base
        int taskId = baseId * 1000 + (int) (Math.random() * 1000);  // Random unique taskId generation
        return taskId;
    }

    private List<ProcessInstanceTask> createProcessInstanceList(HttpServletRequest request, User principal, List<WorkFlowPayLoad> workFlowPayLoads) throws Exception {
        List<ProcessInstanceTask> processInstanceTasks = new ArrayList<>();

        for (WorkFlowPayLoad payload : workFlowPayLoads) {
            // Create a new ProcessInstanceTask based on the WorkFlowPayLoad
            ProcessInstanceTask processInstanceTask = new ProcessInstanceTask();

            // Use the previously defined logic to generate an ID
            processInstanceTask.setProcessInstanceId(generateProcessInstanceId(payload));
            processInstanceTask.setId(generateTaskId(payload)); // Assuming you have a logic for generating task ID
            processInstanceTask.setAssignedTo(payload.getNextAssignedTo());
            processInstanceTask.setPriority(payload.getPriority());

            processInstanceTask.setAssignedAt(new Date());
            processInstanceTask.setStartDate(payload.getCreationDate());
            processInstanceTask.setDueDate(payload.getPostponedDate() != null ? payload.getPostponedDate() : new Date());
            processInstanceTask.setPostponedDate(payload.getPostponedDate());
            processInstanceTask.setTaskDate(payload.getCreationDate());
            // Add the created task to the list
            processInstanceTasks.add(processInstanceTask);
        }

        // Return the list of created ProcessInstanceTasks
        return processInstanceTasks;
    }
}




















    /*@SneakyThrows
    private List<ProcessInstanceDTO> createAndCloseProcess(User principal, HttpServletRequest request, List<WorkFlowPayLoad> autoCompleteWorkFlowPlayLoads)
    {
        List<ProcessInstanceTask> processInstanceTaskList = this.constructProcessInstanceTasks(autoCompleteWorkFlowPlayLoads);
        if (CollectionUtils.isEmpty(processInstanceTaskList)) {
            processInstanceTaskList = this.createProcessInstanceList(request, principal, autoCompleteWorkFlowPlayLoads);
        }
        log.info("Trying to auto complete {} ", autoCompleteWorkFlowPlayLoads);
        this.autoComplete(principal, this.constructAutoCompleteDTO(autoCompleteWorkFlowPlayLoads));
        List<ProcessInstanceDTO> processInstanceDTOList = new ArrayList<>();
        processInstanceTaskList.forEach(each -> {
            ProcessInstanceDTO processInstanceDTO = ProcessInstanceDTO.builder().processInstanceTaskId(each.getId()).processInstanceId(each.getProcessInstanceId()).build();
            processInstanceDTOList.add(processInstanceDTO);
        });
        return processInstanceDTOList;
    }
    @Override
    public void autoComplete(User principal, AutoComplete autoComplete) throws CyborgException {
        List<String> references = autoComplete.getReferences();
        List<ProcessInstanceStatus> processInstanceStatuses = Arrays.asList(ProcessInstanceStatus.PENDING,ProcessInstanceStatus.IN_PROGRESS);
        List<ProcessInstance> processInstances =
                processInstanceRepository.getProcessInstances(Collections.singletonList(autoComplete.getProcessId()),
                        processInstanceStatuses, references,
                        null, null, null, null, null);
        if (processInstances == null || processInstances.size() == 0) {
            return;
        }
        autoComplete(processInstances);
    }

    @Override
    public void autoComplete(List<ProcessInstance> processInstances) {
        List<ProcessInstance> modifiedProcessInstances = new ArrayList<>();
        List<ProcessInstanceTask> modifiedProcessInstanceTasks = new ArrayList<>();
        List<Integer> processInstanceIds = new ArrayList<>();
        for(ProcessInstance processInstance : processInstances) {
            log.info("AutoCompleting workflow - " + processInstance.getReference());
            processInstanceIds.add(processInstance.getId());
            processInstance.setStatus(ProcessInstanceStatus.COMPLETED);
            modifiedProcessInstances.add(processInstance);
        }
        if(!modifiedProcessInstances.isEmpty()) {
            updateProcessInstances(processInstances);
        }
        List<ProcessInstanceTask> processInstanceTasks = processInstanceTaskService.getProcessInstanceTasksByProcessInstanceIds(processInstanceIds);

        processInstanceTasks = processInstanceTasks.stream().filter(processInstanceTask -> processInstanceTask.getStatus() != ProcessInstanceTaskStatus.COMPLETED).collect(Collectors.toList());
        for (ProcessInstanceTask processInstanceTask : processInstanceTasks) {
            if (processInstanceTask.getStartDate() == null) {
                processInstanceTask.setStartDate(new Date());
            }
            processInstanceTask.setStatus(ProcessInstanceTaskStatus.COMPLETED);
            processInstanceTask.setDescription("System Completed");
            processInstanceTask.setEndDate(new Date());
            modifiedProcessInstanceTasks.add(processInstanceTask);
        }
        processInstanceTaskService.updateProcessInstanceTasks(modifiedProcessInstanceTasks);
    }

    @Override
    public void updateProcessInstances(List<ProcessInstance> processInstances) {
        processInstanceRepository.saveAll(processInstances);
    }
    @Override
    public List<ProcessInstance> findOpenPIsByReferenceAndProcess(String reference, Integer processId) throws Exception {
        if(StringUtils.isEmpty(reference) || Objects.isNull(processId)) {
            throw new BadRequestException("Mandatory params are missing");
        }
        return processInstanceRepository.fetchOpenPIsByReferenceAndProcess(reference, processId);
    }
    @Override
    public List<ProcessInstanceDTO> findOpenPIByReferenceAndExternalReferenceForOndc(String reference, String externalReference) throws Exception {
        return processInstanceRepository.findOpenPIByReferenceAndExternalReferenceForOndc(reference, externalReference);
    }
    @SneakyThrows
    private AutoComplete constructAutoCompleteDTO(List<WorkFlowPayLoad> autoCompleteWorkFlowPlayLoads) {
        Integer processId = autoCompleteWorkFlowPlayLoads.stream().map(WorkFlowPayLoad::getProcessId).findFirst().orElseThrow(() -> new BadRequestException("Process ID is missing"));
        List<String> references = autoCompleteWorkFlowPlayLoads.stream().map(WorkFlowPayLoad::getReference).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        AutoComplete autoComplete = new AutoComplete();
        autoComplete.setProcessId(processId);
        autoComplete.setReferences(references);
        return autoComplete;
    }*/
    /*@Transactional(rollbackFor = Exception.class)
    public List<ProcessInstanceTask> createProcessInstanceList(HttpServletRequest request, User user, List<WorkFlowPayLoad> workFlowPayLoadList)
            throws Exception {

        if (workFlowPayLoadList == null || workFlowPayLoadList.size() == 0) {
            throw new DataNotFoundException("WorkFlowPayLoadList should not be empty");
        }

        log.info(workFlowPayLoadList.toString());

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

        List<ProcessInstance> existingProcessInstances = processInstanceRepository.
                getProcessInstances(Arrays.asList(processId), statuses, actualReferences, null, null, null, null, referenceTypeList);

        ProcessInstanceHandler handler = processInstanceHandlerFactory.get(processId);
        ConvoxDTO convoxDTO = null;
        if(Objects.nonNull(handler)){
            log.info("Calling ticket creation pre processor for process : {}",processId);
            workFlowPayLoadList = handler.preProcessorTicketCreation(request,user,workFlowPayLoadList);
            ticketCreateValidationFlag = handler.checkTicketCreateValidationRequired(type);
            convoxDTO = handler.convoxDataUploadBuild(request, workFlowPayLoadList.get(0));
        }

        if(processId == 160) {
            if(type == 0) {
                workFlowPayLoadList = processInstanceServiceHelper.validateWorkFlowPayloadsOnReference(workFlowPayLoadList,existingProcessInstances);
            } else {
                processInstanceServiceHelper.validateOndcChildTickets(workFlowPayLoadList);
            }
        } else {
            if(processId == 74){
                workFlowPayLoadList = processInstanceServiceHelper.validateProcessCreationForCentralTeam(workFlowPayLoadList,existingProcessInstances);
            }
            else if (processId == 31 || processId == 40 || !ticketCreateValidationFlag) {
                log.info("No ticket create validation for  processId {} and type {}", processId, type);
            } else if(processId == 33 || processId == 39 || processId == 44 ) {
                workFlowPayLoadList = processInstanceServiceHelper.validateWorkFlowPayloadsOnCreationDateAndReference(workFlowPayLoadList, existingProcessInstances);
            } else {
                workFlowPayLoadList = processInstanceServiceHelper.validateWorkFlowPayloadsOnReference(workFlowPayLoadList, existingProcessInstances);
            }
        }


        if (workFlowPayLoadList == null || workFlowPayLoadList.isEmpty()) {

            if (processId == 11 || processId == 21 || processId == 25 || processId == 26 || processId == 29
                    || processId == 30 || processId == 38 || processId == 40 || processId == 46 || processId == 58
                    || disableErrorIfTicketExist) {
                log.info("TRYING TO CREATE NEW TICKET FOR PROCESS ID - " + processId + "  REFERENCE - " + actualReferences);
                return new ArrayList<>();
            } else {
                throw new BadRequestException("Ticket is already in progress. Please wait for some time.");
            }
        }

        List<ProcessInstanceTask> processInstanceTaskList = new ArrayList<>();

        for(WorkFlowPayLoad workFlowPayLoad : workFlowPayLoadList) {
            ProcessInstance processInstance = processCreateWorkFlow(user.getId(), workFlowPayLoad);
            processInstanceVariableService.createProcessInstanceVariables(user.getId(), processInstance.getProcessId(), processInstance.getId(), workFlowPayLoad.getWorkflowVariables());
            int assignToAgentId = getAssignToAgentId(request, workFlowPayLoad, handler);
            ProcessInstanceTask processInstanceTask = processInstanceTaskService.createProcessInstanceTaskForProcessInstance(user.getId(), workFlowPayLoad, processInstance.getId(), workFlowPayLoad.getProcessId(), 1, assignToAgentId, workFlowPayLoad.getCreationDate());
            processInstanceTaskList.add(processInstanceTask);
            if (Objects.nonNull(convoxDTO) && convoxDTO.isDataUploadFlag()) {
                convoxDTO.setPitId(processInstanceTask.getId());
                convoxDTO.setLanguageId(processInstance.getLanguage());
                processInstanceServiceHelper.uploadDataToConvox(convoxDTO);
            }
        }
        // call auto assign api after ticket is created
        executeAutoAssignmentPostTicketCreation(user,request, processInstanceTaskList);
        return processInstanceTaskList;
    }
    @Override
    public List<StakeholderProcessInstanceDTO> getActiveProcessForStakeholder(int stakeholderId , String domainName, List<Integer> ignoreProcessIds, Integer thresholdMinutes) {
        return processInstanceRepository.getActiveProcessForStakeholder(stakeholderId, domainName, ignoreProcessIds, thresholdMinutes);
    }
    private void executeAutoAssignmentPostTicketCreation(User user,HttpServletRequest request, List<ProcessInstanceTask> processInstanceTaskList) throws Exception {
        log.info("CALLING AUTO ASSIGN POST TICKET CREATION");
        for (ProcessInstanceTask processInstanceTask : processInstanceTaskList) {
            Optional<ProcessInstance> processInstance = processInstanceRepository.findById(processInstanceTask.getProcessInstanceId());
            if (processInstance.isPresent()) {
                String loanProductIdsForNewAssignmentLogic = processURLConfiguration.getProperty("custom.auto.assign.loan.products", "");
                List<Integer> loanProductIds = CommonUtils.commaSeparatedStringToList(loanProductIdsForNewAssignmentLogic);
                boolean newAutoAssignEnabled = false;
                Map<String, Object> actionObjectMap = CommonUtils.getObjectMapperObject(processInstance.get().getActionObject());
                if(Objects.nonNull(actionObjectMap) && actionObjectMap.containsKey("refType") && actionObjectMap.containsKey("refId") && loanProductIds.contains(Integer.parseInt(actionObjectMap.get("refId").toString()))){
                    newAutoAssignEnabled = true;
                }
                List<TeamProcessTaskEnum> teamProcessTaskEnumList = ProcessEnum.getTeamProcessTaskEnumByProcessId(processInstance.get().getProcessId());
                if(!CollectionUtils.isEmpty(teamProcessTaskEnumList) && !newAutoAssignEnabled){
                    log.info("TeamProcessTaskEnum exists, calling old function for enum: {}", teamProcessTaskEnumList.toString());
                    autoAssignTaskHelper.autoAssign(user, request, teamProcessTaskEnumList, false);
                }else if(newAutoAssignEnabled) {
                    log.info("new auto assign enabled for the processId : {}, refType : {}, productId : {}", processInstance.get().getProcessId(), actionObjectMap.get("refType"), actionObjectMap.get("refId"));
                    int ticketTypeRefId = 0;
                    String ticketRefType = actionObjectMap.get("refType").toString();
                    try {
                        ticketTypeRefId = Integer.parseInt(actionObjectMap.get("refId").toString());
                    } catch (NumberFormatException ex) {
                        log.info("exception while parsing refId in action object exception: {}", ex.toString());
                    }
                    TeamProcessTaskDTO teamProcessTaskDTO = teamProcessTaskService.getTeamProcessTaskForFilter(processInstanceTask.getProcessTaskId(), processInstanceTask.getType(), ticketTypeRefId, ticketRefType);
                    if(Objects.nonNull(teamProcessTaskDTO)){
                        autoAssignTaskHelper.autoAssignV2(user, request, teamProcessTaskDTO.getTeamId(), false);
                    }
                }else{
                    log.info("TeamProcessTaskEnum does not exists, and trying get TeamProcessTask from DB for processId : {}, type : {}", processInstance.get().getProcessId(), processInstanceTask.getType());
                    List<TeamProcessTaskDTO> teamProcessTaskDTOList = teamProcessTaskService.getTeamProcessTaskForTaskAndTypeId(processInstanceTask.getProcessTaskId(), processInstanceTask.getType());
                    for(TeamProcessTaskDTO teamProcessTaskDTO : teamProcessTaskDTOList){
                        autoAssignTaskHelper.autoAssignV2(user, request, teamProcessTaskDTO.getTeamId(), false);
                    }
                }
            }

        }
    }
    private List<ProcessInstanceTask> constructProcessInstanceTasks(List<WorkFlowPayLoad> autoCompleteWorkFlowPlayLoads) {
        List<ProcessInstanceStatus> statuses = Lists.newArrayList(ProcessInstanceStatus.PENDING, ProcessInstanceStatus.IN_PROGRESS);
        int processId = autoCompleteWorkFlowPlayLoads.iterator().next().getProcessId();
        List<String> actualReferences = processInstanceServiceHelper.getReferences(autoCompleteWorkFlowPlayLoads);
        List<String> referenceTypeList = autoCompleteWorkFlowPlayLoads.stream().map(WorkFlowPayLoad::getReferenceType).collect(Collectors.toList());
        List<ProcessInstance> existingProcessInstances = processInstanceRepository.getProcessInstances(Collections.singletonList(processId), statuses,
                actualReferences, null, null, null, null, referenceTypeList);
        if (CollectionUtils.isEmpty(existingProcessInstances)) {
            return Collections.emptyList();
        }
        List<Integer> processInstanceIds = existingProcessInstances.stream().map(ProcessInstance::getId).collect(Collectors.toList());
        return processInstanceTaskService.getProcessInstanceTasksByProcessInstanceIds(processInstanceIds);
    }

    private ProcessInstance processCreateWorkFlow(int userId, WorkFlowPayLoad workFlowPayLoad) throws CyborgException {
        ProcessInstance processInstance = new ProcessInstance();
        if(workFlowPayLoad.getProcessId() == 171 && workFlowPayLoad.getProcessInstanceId() != null) {
            Optional<ProcessInstance> optionalProcessInstance = processInstanceRepository.findById(workFlowPayLoad.getProcessInstanceId());
            if(optionalProcessInstance.isPresent()) {
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
        if(languagePreference == null || languagePreference.isEmpty()) {
            log.info("SETTING LANGUAGE MANUALLY DURING TICKET CREATION");
            languagePreference = "3";
        }
        processInstance.setLanguage(languagePreference);
        processInstance.setReferenceType(workFlowPayLoad.getReferenceType());
        processInstanceRepository.saveAndFlush(processInstance);
        return processInstance;
    }
    private int getAssignToAgentId(HttpServletRequest request, WorkFlowPayLoad workFlowPayLoad,
                                   ProcessInstanceHandler handler) throws Exception {
        if (Objects.nonNull(handler)) {
            return handler.getAssignedToAgentId(request, workFlowPayLoad);
        }
        else if (Objects.nonNull(workFlowPayLoad.getActionObject())
                && Objects.nonNull(workFlowPayLoad.getActionObject().get("assignedTo"))) {
            return Integer.parseInt(workFlowPayLoad.getActionObject().get("assignedTo").toString());
        }
        return CommonEnum.SYSTEM_USER;
    }

        }*/
