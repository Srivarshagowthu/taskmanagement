package com.ninjacart.task_mgmt_service.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ninjacart.task_mgmt_service.Exception.BadRequestException;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.Utils.DateUtils;
import com.ninjacart.task_mgmt_service.entity.WorkFlowPayLoad;
import com.ninjacart.task_mgmt_service.Service.ProcessInstanceService;
import com.ninjacart.task_mgmt_service.Service.ProcessService;
import com.ninjacart.task_mgmt_service.Service.ProcessInstanceService;
import com.ninjacart.task_mgmt_service.model.ProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.model.ConvoxDTO;
import com.ninjacart.task_mgmt_service.model.ConvoxData;
import com.ninjacart.task_mgmt_service.model.ConvoxDataUploadDTO;
import com.ninjacart.task_mgmt_service.model.StakeholderProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.model.enums.ConvoxCrmQueueNameEnum;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.ninjacart.task_mgmt_service.Exception.LockAcquisitionException;
import com.ninjacart.task_mgmt_service.entity.Process;
import com.ninjacart.task_mgmt_service.entity.ProcessInstance;
import com.ninjacart.task_mgmt_service.entity.ProcessInstanceTask;
import com.ninjacart.task_mgmt_service.entity.Process;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.checkerframework.checker.nullness.Opt.orElseThrow;
@Slf4j
public class ProcessInstanceServiceHelper {
    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private ProcessCoexistenceMapService processCoexistenceMapService;
    @Autowired
    private ProcessInstanceTaskService processInstanceTaskService;
    @Autowired
    private KlawRestService klawRestService;


    private static Cache<Integer, List<Process>> COEXIST_PROCESS_LOCK = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(24, TimeUnit.HOURS).recordStats().build();
    private static Cache<String, Boolean> PROCESS_REFERENCE_LOCK = CacheBuilder.newBuilder().maximumSize(10000).recordStats().build();

    public List<String> getReferences(List<WorkFlowPayLoad> workFlowPayLoads) {
        if (workFlowPayLoads == null) {
            return null;
        }
        List<String> references = workFlowPayLoads.stream().
                map(workFlowPayLoad -> workFlowPayLoad.getReference()).
                collect(Collectors.toList());
        return references;
    }

    public void addCacheForProcessAndReference(Collection<WorkFlowPayLoad> workFlowPayLoads) throws CyborgException {
        List<String> keys = new ArrayList<>();
        for(WorkFlowPayLoad workFlowPayLoad : workFlowPayLoads) {
            String key = workFlowPayLoad.getProcessId() + "_"+ workFlowPayLoad.getReference();
            Boolean keyPresent = PROCESS_REFERENCE_LOCK.getIfPresent(key);
            if( keyPresent!= null && keyPresent) {
                throw new LockAcquisitionException("Ticket is already being created for processId - " + workFlowPayLoad.getProcessId() + " for reference - " + workFlowPayLoad.getReference());
            }else {
                PROCESS_REFERENCE_LOCK.put(key, true);
                keys.add(key);
            }
        }
    }
    public void removeCacheForProcessAndReference(Collection<WorkFlowPayLoad> workFlowPayLoads) {
        for(WorkFlowPayLoad workFlowPayLoad : workFlowPayLoads) {
            String key = workFlowPayLoad.getProcessId() + "_"+ workFlowPayLoad.getReference();
            PROCESS_REFERENCE_LOCK.invalidate(key);
        }
    }
    /*public void validateProcessCreationForStakeholder(List<WorkFlowPayLoad> workFlowPayLoads) throws CyborgException {
        WorkFlowPayLoad workFlowPayLoad = workFlowPayLoads.get(0);
        Map<String, String> workflowVariables = workFlowPayLoad.getWorkflowVariables();
        if (workflowVariables == null || workflowVariables.isEmpty()) {
            return;
        }
        String stakeHolderId = workflowVariables.getOrDefault("STAKEHOLDER_ID", null);
        if (stakeHolderId == null) {
            return;
        }
        int processId = workFlowPayLoad.getProcessId();
        List<Process> coexistProcesses = COEXIST_PROCESS_LOCK.getIfPresent(processId);
        if(coexistProcesses == null || coexistProcesses.isEmpty()) {
            coexistProcesses = processCoexistenceMapService.getMappedProcessForProcess(processId);
            COEXIST_PROCESS_LOCK.put(processId, coexistProcesses);
        }
        Process process = processService.getProcessById(processId);
                //.orElseThrow(() -> new EntityNotFoundException("Process not found for ID: " + processId));
        List<Integer> ignoreProcessIds = new ArrayList<>();
        if(!coexistProcesses.isEmpty()) {
            ignoreProcessIds.addAll(coexistProcesses.stream().map(Process::getId).collect(Collectors.toList()));
        }
        ignoreProcessIds.add(processId);
        List<StakeholderProcessInstanceDTO> stakeholderProcessInstanceDTOS = processInstanceService.getActiveProcessForStakeholder(Integer.parseInt(stakeHolderId), process.getDomain(), ignoreProcessIds, 5);
        if(stakeholderProcessInstanceDTOS != null && !stakeholderProcessInstanceDTOS.isEmpty()) {
            StakeholderProcessInstanceDTO stakeholderProcessInstanceDTO = stakeholderProcessInstanceDTOS.get(0);
            throw new BadRequestException(stakeholderProcessInstanceDTO.getProcessName() + " ticket is in progress. Please wait for some time");
        }
    }
    public List<WorkFlowPayLoad> validateProcessCreationForCentralTeam(List<WorkFlowPayLoad> workFlowPayLoads, List<ProcessInstance> existingProcessInstances) {
        if (workFlowPayLoads == null || workFlowPayLoads.size() == 0) {
            return null;
        }

        if (existingProcessInstances == null || existingProcessInstances.size() == 0) {
            return workFlowPayLoads;
        }

        List<String> actualReferences = getReferences(workFlowPayLoads);

        List<Integer> processInstanceIdList = existingProcessInstances.stream().map(each->each.getId()).collect(Collectors.toList());
        Map<String,ProcessInstance> existingReferenceProcessInstanceMap = existingProcessInstances.stream().collect(Collectors.toMap(each->each.getReference(),each->each));

        List<ProcessInstanceTask> existingProcessInstanceTaskList  = processInstanceTaskService.getProcessInstanceTasksByProcessInstanceIds(processInstanceIdList);

        Map<Integer,Integer> instanceIdPriorityMap = existingProcessInstanceTaskList.stream().collect(Collectors.toMap(each->each.getProcessInstanceId(),each->each.getPriority()));

        Map<String,Integer> referencePriorityMap = existingProcessInstances.stream().collect(Collectors.toMap(each->each.getReference(),each->instanceIdPriorityMap.getOrDefault(each.getId(),0)));

        List<ProcessInstance> toBeOverriddenInstances = new ArrayList<>();
        List<ProcessInstance> toBeOverriddenTasks = new ArrayList<>();

        List<WorkFlowPayLoad> filteredWorkFlowPayloads = new ArrayList<>();

        workFlowPayLoads.forEach(payLoad->{
            if(referencePriorityMap.containsKey(payLoad.getReference())){
                if(payLoad.getPriority() >= referencePriorityMap.get(payLoad.getReference())){
                    toBeOverriddenInstances.add(existingReferenceProcessInstanceMap.get(payLoad.getReference()));
                    filteredWorkFlowPayloads.add(payLoad);
                }
            }else{
                filteredWorkFlowPayloads.add(payLoad);
            }
        });

        if(!toBeOverriddenInstances.isEmpty()){
            toBeOverriddenInstances.forEach(processInstance -> {
                processInstance.setStatus(ProcessInstanceStatus.CLOSED);
            });
            processInstanceService.updateProcessInstances(toBeOverriddenInstances);
        }
        processInstanceService.updateProcessInstances(toBeOverriddenInstances);
        //subtract existingReferences from actualRefernces

        // List<WorkFlowPayLoad> filteredWorkFlowPayloads = workFlowPayLoads.stream().filter(workFlowPayLoad ->  actualReferences.contains(workFlowPayLoad.getReference())).collect(Collectors.toList());

        return filteredWorkFlowPayloads;
    }
    public void validateOndcChildTickets(List<WorkFlowPayLoad> workFlowPayLoads) throws Exception {
        for (WorkFlowPayLoad object : workFlowPayLoads) {
            List<ProcessInstance> processInstances = processInstanceService.findOpenPIsByReferenceAndProcess(object.getReference(), 160);
            if (CollectionUtils.isEmpty(processInstances)) {
                throw new BadRequestException("No parent ticket exists for the given reference: " + object.getReference());
            }
            String externalReference = object.getActionObject().get("externalReferenceId") + "";
            List<ProcessInstanceDTO> externalReferenceList = processInstanceService.findOpenPIByReferenceAndExternalReferenceForOndc(object.getReference(), externalReference);
            if (!CollectionUtils.isEmpty(externalReferenceList)) {
                throw new BadRequestException("There already a child ticket with reference:" + object.getReference() + " and externalReferenceId: " + externalReference);
            }
        }
    }
        public List<WorkFlowPayLoad> validateWorkFlowPayloadsOnReference(List<WorkFlowPayLoad> workFlowPayLoads,
                List<ProcessInstance> processInstances) {
            if (workFlowPayLoads == null || workFlowPayLoads.size() == 0) {
                return null;
            }

            if (processInstances == null || processInstances.size() == 0) {
                return workFlowPayLoads;
            }

            List<String> actualReferences = getReferences(workFlowPayLoads);

            log.info("Actual Reference {}",actualReferences);

            List<String> existingReferences = processInstances.stream().map(ProcessInstance :: getReference).collect(Collectors.toList());

            log.info("Existing Reference {}",existingReferences);

            //subtract existingReferences from actualRefernces

            actualReferences.removeAll(existingReferences);

            log.info("Filtered Reference {}",actualReferences);

            List<WorkFlowPayLoad> filteredWorkFlowPayloads = workFlowPayLoads.stream().filter(workFlowPayLoad ->  actualReferences.contains(workFlowPayLoad.getReference())).collect(Collectors.toList());

            return filteredWorkFlowPayloads;
        }

        public List<WorkFlowPayLoad> validateWorkFlowPayloadsOnCreationDateAndReference(List<WorkFlowPayLoad> workFlowPayLoads,
                List<ProcessInstance> processInstances) {
            if (workFlowPayLoads == null || workFlowPayLoads.size() == 0) {
                return null;
            }

            if (processInstances == null || processInstances.size() == 0) {
                return workFlowPayLoads;
            }

            List<WorkFlowPayLoad> filteredWorkFlowPayloads = new ArrayList<>();
            for(WorkFlowPayLoad workFlowPayLoad : workFlowPayLoads) {
                boolean found = false;
                List<ProcessInstance> existingProcessInstances = processInstances.stream().filter(processInstance -> processInstance.getReference().equals(workFlowPayLoad.getReference())).collect(Collectors.toList());
                if(existingProcessInstances != null && !existingProcessInstances.isEmpty()) {
                    for(ProcessInstance existingProcessInstance : existingProcessInstances) {
                        if(DateUtils.dateFormatSort(existingProcessInstance.getCreatedAt()).equals(DateUtils.dateFormatSort(workFlowPayLoad.getCreationDate()))) {
                            found = true;
                            break;
                        }
                    }
                }
                if(!found) {
                    filteredWorkFlowPayloads.add(workFlowPayLoad);
                }
            }
            return filteredWorkFlowPayloads;

        }
    public void uploadDataToConvox(ConvoxDTO convoxDTO) {
        log.info("Calling convox upload data API for ticketId {}, convoxDTO {}", convoxDTO.getPitId(), convoxDTO);
        ConvoxDataUploadDTO convoxDataUploadDTO = new ConvoxDataUploadDTO();
        ConvoxCrmQueueNameEnum crmQueue = ConvoxCrmQueueNameEnum.findByLanguageId(convoxDTO.getLanguageId());
        ConvoxData convoxData = ConvoxData.builder().mobile(convoxDTO.getContactNumber())
                .priority(convoxDTO.getPriority()).info_3(String.valueOf(convoxDTO.getPitId()))
                .queue_name(crmQueue.getQueueName()).build();
        convoxDataUploadDTO.setData(Collections.singletonList(convoxData));
        try {
            klawRestService.convoxUploadData(convoxDataUploadDTO, convoxDTO.getPitId());
        } catch (Exception e) {
            log.error("Error occurred while calling convox data upload API " + e);
        }
    }*/


    }
