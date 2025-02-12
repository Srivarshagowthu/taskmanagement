package com.sky.cloud.service;

import com.sky.cloud.dto.ProcessInstanceTaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProcessInstanceTaskService {
    ResponseEntity<ProcessInstanceTaskDTO> getProcessInstanceTaskById(Integer id);
    ResponseEntity<List<ProcessInstanceTaskDTO>> getAllProcessInstanceTasks(Integer page, Integer size);
    ResponseEntity<ProcessInstanceTaskDTO> createProcessInstanceTask(ProcessInstanceTaskDTO processInstanceTaskDTO);
    ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTask(Integer id, ProcessInstanceTaskDTO updatedProcessInstanceTask);
    ResponseEntity<String> deleteProcessInstanceTaskById(Integer id);
    ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTaskStatus(Integer id, ProcessInstanceTaskDTO processInstanceTaskDTO);
}
