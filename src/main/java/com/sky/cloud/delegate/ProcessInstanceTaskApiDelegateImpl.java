package com.sky.cloud.delegate;

import com.sky.cloud.api.ProcessInstanceTasksApiDelegate;
import com.sky.cloud.dto.ProcessInstanceTaskDTO;

import com.sky.cloud.repository.ProcessInstanceTaskRepository;

import com.sky.cloud.service.ProcessInstanceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.beans.PropertyDescriptor;
import java.time.OffsetDateTime;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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


//@Override
//public ResponseEntity<ProcessInstanceTaskDTO> getProcessInstanceTaskById(Integer id) {
//  log.debug("Fetching ProcessInstanceTask with id: {}", id);
//
//  ProcessInstanceTaskDTO processInstanceTaskDTO = processInstanceTaskRepository.findByIdAndDeleted(id, (byte) 0);
//  if (processInstanceTaskDTO == null) {
//
//    return ResponseEntity.notFound().build();
//  }
//  log.debug("Found ProcessInstanceTask: {}", processInstanceTaskDTO);
//  return ResponseEntity.ok(processInstanceTaskDTO);
//}
//
//  @Override
//  public ResponseEntity<List<ProcessInstanceTaskDTO>> getAllProcessInstanceTasks(Integer page, Integer size) {
//    log.debug("Fetching all ProcessInstanceTasks with page: {}, size: {}", page, size);
//
//    Page<ProcessInstanceTaskDTO> paginatedTasks = processInstanceTaskRepository.findByDeletedNot((int) 1, PageRequest.of(page, size));
//    if (paginatedTasks.isEmpty()) {
//      log.debug("No ProcessInstanceTasks found");
//      return ResponseEntity.noContent().build();
//    }
//    log.debug("Found {} ProcessInstanceTasks", paginatedTasks.getContent().size());
//    return ResponseEntity.ok(paginatedTasks.getContent());
//  }
//
//  @Override
//  public ResponseEntity<ProcessInstanceTaskDTO> createProcessInstanceTasks(@RequestBody ProcessInstanceTaskDTO processInstanceTaskDTO) {
//    log.debug("Creating new ProcessInstanceTask: {}", processInstanceTaskDTO);
//
//    if (processInstanceTaskDTO == null || processInstanceTaskDTO.getName() == null || processInstanceTaskDTO.getDescription() == null) {
//      log.debug("Invalid ProcessInstanceTask data: {}", processInstanceTaskDTO);
//      return ResponseEntity.badRequest().build();
//    }
//
//    if (processInstanceTaskDTO.getCreatedAt() == null) {
//      processInstanceTaskDTO.setCreatedAt(OffsetDateTime.now(ZoneOffset.ofHoursMinutes(5, 30)));
//    }
//    processInstanceTaskDTO.setUpdatedAt(OffsetDateTime.now(ZoneOffset.ofHoursMinutes(5, 30)));
//    processInstanceTaskDTO.setDeleted(0);
//
//    ProcessInstanceTaskDTO savedTask = processInstanceTaskRepository.save(processInstanceTaskDTO);
//    log.debug("ProcessInstanceTask created successfully with id: {}", savedTask.getId());
//    return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
//  }
//
//  @Override
//  public ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTask(Integer id, ProcessInstanceTaskDTO updatedProcessInstanceTask) {
//    log.debug("Updating ProcessInstanceTask with id: {}", id);
//
//    ProcessInstanceTaskDTO existingTask = processInstanceTaskRepository.findByIdAndDeleted(id, 0);
//    if (existingTask == null) {
//      log.debug("ProcessInstanceTask with id {} not found", id);
//      return ResponseEntity.notFound().build();
//    }
//
//    BeanUtils.copyProperties(updatedProcessInstanceTask, existingTask, getNullPropertyNames(updatedProcessInstanceTask, "createdAt", "deleted"));
//    existingTask.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());
//
//    ProcessInstanceTaskDTO savedTask = processInstanceTaskRepository.save(existingTask);
//    log.debug("Updated ProcessInstanceTask: {}", savedTask);
//    return ResponseEntity.ok(savedTask);
//  }
//
//  private String[] getNullPropertyNames(ProcessInstanceTaskDTO source, String... excludeFields) {
//    final BeanWrapper src = new BeanWrapperImpl(source);
//    Set<String> nullPropertyNames = new HashSet<>(Arrays.asList(excludeFields));
//
//    for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
//      if (src.getPropertyValue(pd.getName()) == null) {
//        nullPropertyNames.add(pd.getName());
//      }
//    }
//    return nullPropertyNames.toArray(new String[0]);
//  }
//
//  @Override
//  public ResponseEntity<String> deleteProcessInstanceTaskById(Integer id) {
//    log.debug("Deleting ProcessInstanceTask with id: {}", id);
//
//    return processInstanceTaskRepository.findById(id)
//            .map(task -> {
//              task.setDeleted(1);
//              processInstanceTaskRepository.save(task);
//              log.debug("ProcessInstanceTask with id {} marked as deleted", id);
//              return ResponseEntity.ok("Task deleted successfully");
//            })
//            .orElseGet(() -> {
//              log.debug("ProcessInstanceTask with id {} not found", id);
//              return ResponseEntity.notFound().build();
//            });
//  }
//
//  @Override
//  public ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTaskStatus(Integer id, ProcessInstanceTaskDTO processInstanceTaskDTO) {
//    log.debug("Updating status of ProcessInstanceTask with id: {}", id);
//
//    if (id == null || processInstanceTaskDTO.getStatus() == null) {
//      log.debug("Invalid status update request: id={}, status={}", id, processInstanceTaskDTO.getStatus());
//      return ResponseEntity.badRequest().body(null);
//    }
//
//    ProcessInstanceTaskDTO existingTask = processInstanceTaskRepository.findByIdAndDeleted(id, 0);
//    if (existingTask == null) {
//
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//    }
//
//    existingTask.setStatus(processInstanceTaskDTO.getStatus());
//    ProcessInstanceTaskDTO updatedTask = processInstanceTaskRepository.save(existingTask);
//    log.debug("Updated status of ProcessInstanceTask: {}", updatedTask);
//    return ResponseEntity.ok(updatedTask);
//  }


