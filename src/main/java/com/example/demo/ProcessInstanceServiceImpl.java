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
    public List<ProcessInstanceDTO> createProcess(CreateProcessRequest createProcessRequest) throws Exception {
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
        processInstanceDTO.setProcessId(workFlowPayLoad.getProcessId());
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
        task.setAssignee(String.valueOf(assignToAgentId));
        task.setCreationDate(new Date());
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
    public ResponseEntity<List<Map<String, Object>>> getProcessInstanceStatuses() {
        return null;
    }

    @Override
    public ResponseEntity<Void> switchAssigneeForCMT(Integer currentAssignee, Integer targetAssignee) {
        return null;
    }
    @Override
    public ResponseEntity<Void> updateTicket(Boolean createNew, UpdateActionObject updateActionObject) {
        if (updateActionObject == null) {
            log.info("Invalid request: updateActionObject is null");
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        if (Boolean.TRUE.equals(createNew)) {
            // Creating a new ticket
            ProcessInstanceDTO newTicket = new ProcessInstanceDTO();
            setTicketFields(newTicket, updateActionObject);
            log.info("Saving new ticket with processInstanceId: {}", updateActionObject.getProcessInstanceId());
            processInstanceServiceRepository.save(newTicket);
            return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
        } else {
            // Updating an existing ticket
            log.info("Updating ticket with processInstanceId: {}", updateActionObject.getProcessInstanceId());
            Optional<ProcessInstanceDTO> existingTicketOpt =
                    processInstanceServiceRepository.findByProcessInstanceId(updateActionObject.getProcessInstanceId());
            log.info("process fpund");

            if (existingTicketOpt.isPresent()) {
                ProcessInstanceDTO existingTicket = existingTicketOpt.get();

                boolean isUpdated = updateTicketFields(existingTicket, updateActionObject);
                log.info("isUpdated value: {}", isUpdated);

                if (isUpdated) {
                    log.info("Updating ticket in database...");
                    processInstanceServiceRepository.save(existingTicket);
                    return ResponseEntity.ok().build(); // 200 OK
                } else {
                    log.info("No changes detected, ticket not modified.");
                    return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build(); // 304 Not Modified
                }
            } else {
                log.info("Ticket not found for processInstanceId: {}", updateActionObject.getProcessInstanceId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
            }
        }
    }

    /**
     * Helper method to set ticket fields when creating a new ticket
     */
    private void setTicketFields(ProcessInstanceDTO ticket, UpdateActionObject updateActionObject) {
        ticket.setProcessInstanceId(updateActionObject.getProcessInstanceId());
        ticket.setStatus(updateActionObject.getPiStatus());
        ticket.setComment(updateActionObject.getComment());
        ticket.setClassificationId(updateActionObject.getClassificationId());
        ticket.setUserId(updateActionObject.getUserId());
        ticket.setReference(updateActionObject.getReference());
        ticket.setReferenceType(updateActionObject.getReferenceType());
        ticket.setPriority(updateActionObject.getPriority());
        ticket.setId(updateActionObject.getId());
    }

    /**
     * Helper method to update ticket fields. Returns true if any field is updated.
     */
    private boolean updateTicketFields(ProcessInstanceDTO existingTicket, UpdateActionObject updateActionObject) {
        boolean isUpdated = false;

        // Update fields using the updateField method
        isUpdated |= updateField(existingTicket::getStatus, updateActionObject.getPiStatus(), existingTicket::setStatus);
        isUpdated |= updateField(existingTicket::getComment, updateActionObject.getComment(), existingTicket::setComment);
        isUpdated |= updateField(existingTicket::getClassificationId, updateActionObject.getClassificationId(), existingTicket::setClassificationId);
        isUpdated |= updateField(existingTicket::getUserId, updateActionObject.getUserId(), existingTicket::setUserId);
        isUpdated |= updateField(existingTicket::getReference, updateActionObject.getReference(), existingTicket::setReference);
        isUpdated |= updateField(existingTicket::getReferenceType, updateActionObject.getReferenceType(), existingTicket::setReferenceType);
        isUpdated |= updateField(existingTicket::getPriority, updateActionObject.getPriority(), existingTicket::setPriority);
        isUpdated |= updateField(existingTicket::getId, updateActionObject.getId(), existingTicket::setId);

        return isUpdated;
    }

    /**
     * Helper method to compare and set fields with specific types.
     */
    private <T> boolean updateField(Supplier<T> currentFieldGetter, T newFieldValue, Consumer<T> fieldSetter) {
        if (!Objects.equals(currentFieldGetter.get(), newFieldValue)) {
            fieldSetter.accept(newFieldValue);
            return true;
        }
        return false;
    }


    @Override
    public ResponseEntity<ProcessInstanceTaskDTO> getTaskDetails(Integer taskId) {
        return null;
    }

}
