package com.sky.cloud.delegate;

import com.sky.cloud.api.ProcessInstanceTasksApiDelegate;
import com.sky.cloud.dto.ProcessInstanceTaskDTO;

import com.sky.cloud.repository.ProcessInstanceTaskRepository;

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

@Service
@Slf4j
public class ProcessInstanceTaskApiDelegateImpl implements ProcessInstanceTasksApiDelegate {
  private ProcessInstanceTaskDTO processInstanceTaskDTO;
  @Autowired private ProcessInstanceTaskRepository processInstanceTaskRepository;

  @Autowired
  public ProcessInstanceTaskApiDelegateImpl(
      ProcessInstanceTaskRepository processInstanceTaskRepository) {
    this.processInstanceTaskRepository = processInstanceTaskRepository;
  }

  @Override
  public ResponseEntity<ProcessInstanceTaskDTO> getProcessInstanceTaskById(Integer id) {

    ProcessInstanceTaskDTO processInstanceTaskDTO =
        processInstanceTaskRepository.findByIdAndDeleted(id, (byte) 0);
    if (processInstanceTaskDTO == null) {

      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(processInstanceTaskDTO);
  }

  @Override
  public ResponseEntity<List<ProcessInstanceTaskDTO>> getAllProcessInstanceTasks(
      Integer page, Integer size) {

    Page<ProcessInstanceTaskDTO> paginatedTasks =
        processInstanceTaskRepository.findByDeletedNot((int) 1, PageRequest.of(page, size));

    if (paginatedTasks.isEmpty()) {

      return ResponseEntity.noContent().build(); // Return 204 if no tasks found
    }

    return ResponseEntity.ok(paginatedTasks.getContent());
  }

  @Override
  public ResponseEntity<ProcessInstanceTaskDTO> createProcessInstanceTasks(
      @RequestBody ProcessInstanceTaskDTO processInstanceTaskDTO) {

    if (processInstanceTaskDTO == null
        || processInstanceTaskDTO.getName() == null
        || processInstanceTaskDTO.getDescription() == null) {

      return ResponseEntity.badRequest().build(); // Return 400 if input is invalid
    }

    if (processInstanceTaskDTO.getCreatedAt() == null) {
      processInstanceTaskDTO.setCreatedAt(
          OffsetDateTime.now(
              ZoneOffset.ofHoursMinutes(5, 30))); // Set createdAt to current time (IST)
    }
    processInstanceTaskDTO.setUpdatedAt(
        OffsetDateTime.now(
            ZoneOffset.ofHoursMinutes(5, 30))); // Set updatedAt to current time (IST)

    processInstanceTaskDTO.setDeleted((int) 0);

    ProcessInstanceTaskDTO savedTask = processInstanceTaskRepository.save(processInstanceTaskDTO);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(savedTask); // Return 201 Created with saved task
  }

  @Override
  public ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTask(
      Integer id, ProcessInstanceTaskDTO updatedProcessInstanceTask) {

    ProcessInstanceTaskDTO existingTask = processInstanceTaskRepository.findByIdAndDeleted(id, 0);

    if (existingTask == null) {

      return ResponseEntity.notFound().build();
    }

    // Copy non-null properties while preserving 'createdAt' & preventing 'deleted' modifications
    BeanUtils.copyProperties(
        updatedProcessInstanceTask,
        existingTask,
        getNullPropertyNames(updatedProcessInstanceTask, "createdAt", "deleted"));

    existingTask.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());

    ProcessInstanceTaskDTO savedTask = processInstanceTaskRepository.save(existingTask);

    return ResponseEntity.ok(savedTask);
  }

  private String[] getNullPropertyNames(ProcessInstanceTaskDTO source, String... excludeFields) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    Set<String> nullPropertyNames = new HashSet<>(Arrays.asList(excludeFields));

    for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
      if (src.getPropertyValue(pd.getName()) == null) {
        nullPropertyNames.add(pd.getName());
      }
    }

    return nullPropertyNames.toArray(new String[0]);
  }

  @Override
  public ResponseEntity<String> deleteProcessInstanceTaskById(Integer id) {

    return processInstanceTaskRepository
        .findById(id)
        .map(
            task -> {
              task.setDeleted(1); // Mark as deleted
              processInstanceTaskRepository.save(task); // Save the updated task with deleted flag

              return ResponseEntity.ok("Task deleted successfully"); // Return success message
            })
        .orElseGet(
            () -> {
              return ResponseEntity.notFound().build();
            });
  }

  @Override
  public ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTaskStatus(Integer id, ProcessInstanceTaskDTO processInstanceTaskDTO) {

    if (id == null || processInstanceTaskDTO.getStatus() == null) {
      return ResponseEntity.badRequest().body(null);
    }
    ProcessInstanceTaskDTO existingTask = processInstanceTaskRepository.findByIdAndDeleted(id, 0);

    if (existingTask == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    existingTask.setStatus(processInstanceTaskDTO.getStatus());

    ProcessInstanceTaskDTO updatedTask = processInstanceTaskRepository.save(existingTask);

    return ResponseEntity.ok(updatedTask);
  }

}
