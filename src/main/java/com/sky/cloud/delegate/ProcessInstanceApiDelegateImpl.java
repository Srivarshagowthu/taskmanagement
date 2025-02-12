package com.sky.cloud.delegate;

import com.sky.cloud.dto.ProcessInstanceTaskDTO;
import com.sky.cloud.service.ProcessInstanceService;
import com.sky.cloud.api1.ProcessinstanceApiDelegate;
import com.sky.cloud.dto1.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ProcessInstanceApiDelegateImpl implements ProcessinstanceApiDelegate {
    private final ProcessInstanceService processInstanceService;
    public ProcessInstanceApiDelegateImpl(ProcessInstanceService processInstanceService) {
        this.processInstanceService = processInstanceService;
    }

    @Override
    public ResponseEntity<List<ProcessInstanceDTO>> createProcess(CreateProcessRequest createProcessRequest) {
        List<ProcessInstanceDTO> createdProcesses = processInstanceService.createProcess(createProcessRequest);
        return ResponseEntity.ok(createdProcesses);
    }

    @Override
    public ResponseEntity<Void> autoCloseTickets(String processIds, String statuses) {
        try {
            processInstanceService.autoCloseTickets(processIds, statuses);
            return new ResponseEntity<>(HttpStatus.OK); // Return 200 OK if successful
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle error
        }
    }

    @Override
    public ResponseEntity<Void> autoCompleteTickets(AutoComplete autoComplete) {
        try {
            processInstanceService.autoCompleteTickets(autoComplete);
            return new ResponseEntity<>(HttpStatus.OK); // Return 200 OK if successful
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Handle error
        }
    }
    @Override
    public ResponseEntity<Void> autoExpireTickets() {
        try {
            processInstanceService.autoExpireTickets();
            return new ResponseEntity<>(HttpStatus.OK); // Return 200 OK if successful
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle error
        }
    }

    @Override
    public ResponseEntity<CloseTickets200Response> closeTickets(List<Integer> processId) {
        try {
            int closedCount = processInstanceService.closeTickets(processId).getStatusCodeValue();
            CloseTickets200Response response = new CloseTickets200Response();
            response.setCount(closedCount);
            return new ResponseEntity<>(response, HttpStatus.OK); // Return 200 OK with response
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle error
        }
    }

    @Override
    public ResponseEntity<List<ProcessInstanceTaskDTO>> getProcessInstanceDetails(Integer processInstanceId) {
        try {
            List<ProcessInstanceTaskDTO> taskDetails = (List<ProcessInstanceTaskDTO>) processInstanceService.getProcessInstanceDetails(processInstanceId);
            return new ResponseEntity<>(taskDetails, HttpStatus.OK); // Return 200 OK with task details
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Handle error
        }
    }


    @Override
    public ResponseEntity<Void> switchAssigneeForCMT(Integer currentAssignee, Integer targetAssignee) {
        try {
            processInstanceService.switchAssigneeForCMT(currentAssignee, targetAssignee);
            return new ResponseEntity<>(HttpStatus.OK); // Return 200 OK if successful
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Handle error
        }
    }


}
