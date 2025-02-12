package com.sky.cloud.impl;

import com.sky.cloud.dto.ProcessInstanceTaskDTO;
import com.sky.cloud.dto1.*;
import com.sky.cloud.service.ProcessInstanceService;
import com.sky.cloud.repository.ProcessInstanceServiceRepository;

import com.sky.cloud.service.ProcessInstanceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@Slf4j
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    @Autowired
    private ProcessInstanceServiceRepository processInstanceServiceRepository;

    @Autowired
    private ProcessInstanceTaskService processInstanceTaskService;

    @Override
    public List<ProcessInstanceDTO> createProcess(CreateProcessRequest createProcessRequest) {
        if (createProcessRequest == null || createProcessRequest.getWorkFlowPayLoads() == null || createProcessRequest.getWorkFlowPayLoads().isEmpty()) {
            throw new BadRequestException("Invalid Request - WorkFlowPayLoads cannot be empty");
        }
        return createProcessInstanceList(createProcessRequest);
    }

    @Override
    public ResponseEntity<Void> autoCloseTickets(String processIds, String statuses) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ProcessInstanceDTO> createProcessInstanceList(CreateProcessRequest createProcessRequest) throws Exception {
        if (createProcessRequest.getWorkFlowPayLoads().isEmpty()) {
            throw new BadRequestException("WorkFlowPayLoadList should not be empty");
        }

        List<ProcessInstanceDTO> processInstanceDTOList = new ArrayList<>();

        for (WorkFlowPayLoad workFlowPayLoad : createProcessRequest.getWorkFlowPayLoads()) {
            ProcessInstanceDTO processInstanceDTO = createProcessInstance(workFlowPayLoad);
            processInstanceDTOList.add(processInstanceDTO);
            createProcessInstanceTask(workFlowPayLoad, processInstanceDTO);
        }
        return processInstanceDTOList;
    }

    private ProcessInstanceDTO createProcessInstance(WorkFlowPayLoad workFlowPayLoad) {
        ProcessInstanceDTO processInstanceDTO = new ProcessInstanceDTO();
        processInstanceDTO.setReference(workFlowPayLoad.getReference());
       // processInstanceDTO.setProcessId(workFlowPayLoad.getProcessId());
        processInstanceDTO.setReferenceType(workFlowPayLoad.getReferenceType());
        processInstanceDTO.setPriority(workFlowPayLoad.getPriority());
        processInstanceDTO.setStatus(workFlowPayLoad.getPiStatus());
        processInstanceDTO.setComment(workFlowPayLoad.getComment());
        processInstanceDTO.setClassificationId(workFlowPayLoad.getClassificationId());
        processInstanceServiceRepository.save(processInstanceDTO);
        return processInstanceDTO;
    }

    private void createProcessInstanceTask(WorkFlowPayLoad workFlowPayLoad, ProcessInstanceDTO processInstanceDTO) {
        int assignToAgentId = determineAgent(workFlowPayLoad);
        ProcessInstanceTaskDTO task = new ProcessInstanceTaskDTO();
        task.setProcessInstanceId(processInstanceDTO.getProcessInstanceId());
        task.assignedTo(Integer.valueOf(String.valueOf(assignToAgentId)));

        processInstanceTaskService.save(task);
    }

    private int determineAgent(WorkFlowPayLoad workFlowPayLoad) {
        return 0;
    }

    @Override
    public ResponseEntity<Void> autoCompleteTickets(AutoComplete autoComplete) {
        // Logic for auto-completing tickets
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> autoExpireTickets() {
        // Logic for auto-expiring tickets
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CloseTickets200Response> closeTickets(List<Integer> processIds) {
        // Logic for closing tickets based on the provided processIds
        CloseTickets200Response response = new CloseTickets200Response();
        // Populate the response accordingly
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<ProcessInstanceTaskDTO>> getProcessInstanceDetails(Integer processInstanceId) {
        return null;
    }



    @Override
    public ResponseEntity<Void> switchAssigneeForCMT(Integer currentAssignee, Integer targetAssignee) {
        return null;
    }
    /**
     * Helper method to set ticket fields when creating a new ticket
     */


    @Override
    public ResponseEntity<ProcessInstanceTaskDTO> getTaskDetails(Integer taskId) {
        return null;
    }

}
