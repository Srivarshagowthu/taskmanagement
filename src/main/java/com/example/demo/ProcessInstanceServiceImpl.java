package com.example.demo;

import com.example.demo.Exceptions.BadRequestException;
import com.example.demo.dto1.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Slf4j
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

  @Autowired private ProcessInstanceServiceRepository processInstanceServiceRepository;

  @Autowired private ProcessInstanceTaskService processInstanceTaskService;

  @Override
  public List<ProcessInstanceDTO> createProcess(CreateProcessRequest createProcessRequest)
      throws Exception {
    // Validate request: Ensure WorkFlowPayLoads is not empty
    if (createProcessRequest == null
        || createProcessRequest.getWorkFlowPayLoads() == null
        || createProcessRequest.getWorkFlowPayLoads().isEmpty()) {
      throw new BadRequestException("Invalid Request - WorkFlowPayLoads cannot be empty");
    }

    // Validate that no fields in WorkFlowPayLoad are null
    for (WorkFlowPayLoad workFlowPayLoad : createProcessRequest.getWorkFlowPayLoads()) {
      if (isAnyFieldNull(workFlowPayLoad)) {
        throw new BadRequestException("Invalid Request - WorkFlowPayLoad fields cannot be null");
      }
    }

    // Proceed with creating process instances
    return createProcessInstanceList(createProcessRequest);
  }

  private boolean isAnyFieldNull(WorkFlowPayLoad workFlowPayLoad) {
    return workFlowPayLoad.getReference() == null
        || workFlowPayLoad.getProcessId() == null
        || workFlowPayLoad.getReferenceType() == null
        || workFlowPayLoad.getPriority() == null
        || workFlowPayLoad.getPiStatus() == null
        || workFlowPayLoad.getComment() == null
        || workFlowPayLoad.getClassificationId() == null;
  }

  @Transactional(rollbackFor = Exception.class)
  public List<ProcessInstanceDTO> createProcessInstanceList(
      CreateProcessRequest createProcessRequest) throws Exception {
    if (createProcessRequest.getWorkFlowPayLoads().isEmpty()) {
      throw new BadRequestException("WorkFlowPayLoadList should not be empty");
    }

    List<ProcessInstanceDTO> processInstanceDTOList = new ArrayList<>();
    for (WorkFlowPayLoad workFlowPayLoad : createProcessRequest.getWorkFlowPayLoads()) {
      // Check for existing process instance by ID
      if (processInstanceServiceRepository.existsByProcessId(workFlowPayLoad.getProcessId())) {
        throw new BadRequestException(
            "Process with ID " + workFlowPayLoad.getProcessId() + " already exists");
      }

      // Create a new Process Instance
      ProcessInstanceDTO processInstanceDTO = createProcessInstance(workFlowPayLoad);
      processInstanceDTOList.add(processInstanceDTO);
      createProcessInstanceTask(workFlowPayLoad, processInstanceDTO);
    }
    return processInstanceDTOList;
  }

  private ProcessInstanceDTO createProcessInstance(WorkFlowPayLoad workFlowPayLoad) {
    ProcessInstanceDTO processInstanceDTO = new ProcessInstanceDTO();
    processInstanceDTO.setReference(workFlowPayLoad.getReference());
    processInstanceDTO.setProcessId(workFlowPayLoad.getProcessId());
    processInstanceDTO.setReferenceType(workFlowPayLoad.getReferenceType());
    processInstanceDTO.setPriority(workFlowPayLoad.getPriority());
    processInstanceDTO.setStatus(workFlowPayLoad.getPiStatus());
    processInstanceDTO.setComment(workFlowPayLoad.getComment());
    processInstanceDTO.setClassificationId(workFlowPayLoad.getClassificationId());

    // Save the process instance in the database
    processInstanceServiceRepository.save(processInstanceDTO);
    return processInstanceDTO;
  }

  private void createProcessInstanceTask(
      WorkFlowPayLoad workFlowPayLoad, ProcessInstanceDTO processInstanceDTO) {
    int assignToAgentId = determineAgent(workFlowPayLoad);
    ProcessInstanceTaskDTO task = new ProcessInstanceTaskDTO();
    task.setProcessInstanceId(processInstanceDTO.getProcessInstanceId());
    task.setAssignee(String.valueOf(assignToAgentId));
    task.setCreationDate(new Date());

    // Save the task in the database
    processInstanceTaskService.save(task);
  }

  @Override
  public ResponseEntity<Void> autoCloseTickets(String processIds, String statuses) {
    // Logic for auto-closing tickets based on the provided processIds and statuses
    return ResponseEntity.ok().build();
  }

  private int determineAgent(WorkFlowPayLoad workFlowPayLoad) {
    // Logic for determining the agent
    return 0; // Placeholder
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
  public ResponseEntity<List<ProcessInstanceTaskDTO>> getProcessInstanceDetails(
      Integer processInstanceId) {
    return null;
  }

  @Override
  public ResponseEntity<List<Map<String, Object>>> getProcessInstanceStatuses() {
    return null;
  }

  @Override
  public ResponseEntity<Void> switchAssigneeForCMT(
      Integer currentAssignee, Integer targetAssignee) {
    return null;
  }

  @Override
  public ResponseEntity<ProcessInstanceTaskDTO> getTaskDetails(Integer taskId) {
    return null;
  }
}
