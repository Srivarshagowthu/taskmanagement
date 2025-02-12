package com.sky.cloud.delegate;

import com.sky.cloud.api.ProcessInstanceTasksApiDelegate;
import com.sky.cloud.dto.ProcessInstanceTaskDTO;

import com.sky.cloud.repository.ProcessInstanceTaskRepository;

import com.sky.cloud.service.ProcessInstanceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class ProcessInstanceTaskApiDelegateImpl implements ProcessInstanceTasksApiDelegate {
@Autowired
  private final ProcessInstanceTaskService processInstanceTaskService;

  @Autowired
  public ProcessInstanceTaskApiDelegateImpl(ProcessInstanceTaskService processInstanceTaskService) {
    this.processInstanceTaskService = processInstanceTaskService;
  }

  @Override
  public ResponseEntity<ProcessInstanceTaskDTO> getProcessInstanceTaskById(Integer id) {
    return processInstanceTaskService.getProcessInstanceTaskById(id);
  }

  @Override
  public ResponseEntity<List<ProcessInstanceTaskDTO>> getAllProcessInstanceTasks(Integer page, Integer size) {
    return processInstanceTaskService.getAllProcessInstanceTasks(page, size);
  }

  @Override
  public ResponseEntity<ProcessInstanceTaskDTO> createProcessInstanceTasks(ProcessInstanceTaskDTO processInstanceTaskDTO) {
    return processInstanceTaskService.createProcessInstanceTask(processInstanceTaskDTO);
  }

  @Override
  public ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTask(Integer id, ProcessInstanceTaskDTO updatedProcessInstanceTask) {
    return processInstanceTaskService.updateProcessInstanceTask(id, updatedProcessInstanceTask);
  }

  @Override
  public ResponseEntity<String> deleteProcessInstanceTaskById(Integer id) {
    return processInstanceTaskService.deleteProcessInstanceTaskById(id);
  }

  @Override
  public ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTaskStatus(Integer id, ProcessInstanceTaskDTO processInstanceTaskDTO) {
    return processInstanceTaskService.updateProcessInstanceTaskStatus(id, processInstanceTaskDTO);
  }
}


