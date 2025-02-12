package com.example.demo;

import com.example.demo.dto1.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProcessInstanceService {
    List<ProcessInstanceDTO> createProcess(CreateProcessRequest createProcessRequest) throws Exception;

ResponseEntity<Void> autoCloseTickets(String processIds, String statuses);

    ResponseEntity<Void> autoCompleteTickets(AutoComplete autoComplete);

    ResponseEntity<Void> autoExpireTickets();

    ResponseEntity<CloseTickets200Response> closeTickets(List<Integer> processIds);

    ResponseEntity<List<ProcessInstanceTaskDTO>> getProcessInstanceDetails(Integer processInstanceId);

    ResponseEntity<List<Map<String, Object>>> getProcessInstanceStatuses();

    ResponseEntity<Void> switchAssigneeForCMT(Integer currentAssignee, Integer targetAssignee);

    ResponseEntity<Void> updateTicket(Boolean createNew, UpdateActionObject updateActionObject);

    ResponseEntity<ProcessInstanceTaskDTO> getTaskDetails(Integer taskId);
}
