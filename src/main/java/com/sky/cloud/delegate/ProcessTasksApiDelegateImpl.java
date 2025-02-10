package com.sky.cloud.delegate;

import com.sky.cloud.api.ProcessTasksApiDelegate;

import com.sky.cloud.dto.ProcessInstanceTaskDTO;
import com.sky.cloud.dto.ProcessTaskDTO;

import com.sky.cloud.repository.ProcessTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class ProcessTasksApiDelegateImpl implements ProcessTasksApiDelegate {
  @Autowired
private RedissonClient redissonClient;
@Autowired
  private ProcessTaskRepository processTaskRepository;


  @Override
  public ResponseEntity<ProcessTaskDTO> getProcessTaskById(Integer ptid) {

    ProcessTaskDTO processTaskDTO = processTaskRepository.findByIdAndDeleted(ptid, (int) 0);

    if (processTaskDTO != null) {

      return ResponseEntity.ok(processTaskDTO);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
//  Prevents duplicate task creation when multiple users send the same tasks.
//Ensures consistency by locking each task individually.
// Handles concurrent API calls effectively using Redis distributed locking.
  @Override
  public ResponseEntity<List<ProcessTaskDTO>> createProcessTasks(@RequestBody List<ProcessTaskDTO> processDTOs) {
    if (processDTOs == null || processDTOs.isEmpty()) {

      return ResponseEntity.badRequest().body(null); // Return 400 if input is invalid
    }

    List<ProcessTaskDTO> validProcessTasks = new ArrayList<>();
    for (ProcessTaskDTO processDTO : processDTOs) {
      if (processDTO.getName() == null || processDTO.getDescription() == null) {

        return ResponseEntity.badRequest().body(null); // Return 400
      }
      if (processDTO.getCreatedAt() == null) {
        processDTO.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());
      }
      processDTO.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());
      processDTO.setDeleted((int) 0);
      String lockkey = "Process Task Lock " + processDTO.getTaskId();
      RLock rLock = redissonClient.getFairLock(lockkey);
      try{
        if (rLock.tryLock(5, 10, TimeUnit.SECONDS)) {
          try{
            validProcessTasks.add(processDTO);
            log.debug("Locked and Processing Task{}", processDTO.getTaskId());
          }finally{
            rLock.unlock();
          }
        }else{
log.debug("Could Not Acquire lock for process task with process task id{}", processDTO.getTaskId());
        }
      }catch (InterruptedException e){
Thread.currentThread().interrupt();
        log.debug("Error acquiring lock for process task with process task id{}", processDTO.getTaskId());
      }

    }

    List<ProcessTaskDTO> savedProcessDTOs = processTaskRepository.saveAll(validProcessTasks);
    return ResponseEntity.status(201).body(savedProcessDTOs);
  }

  @Override
  public ResponseEntity<String> deleteProcessTaskById(Integer ptid) {
    return processTaskRepository
            .findById(ptid)
            .map(task -> {
              task.setDeleted(1); // Mark as deleted
              processTaskRepository.save(task); // Save the updated task with deleted flag

              return ResponseEntity.ok("Task deleted successfully"); // Return success message
            })
            .orElseGet(() -> {

              return ResponseEntity.notFound().build(); // Return 404 if task not found
            });
  }

  @Override
  public ResponseEntity<List<ProcessTaskDTO>> getAllProcessTasks(Integer page, Integer size) {

    Page<ProcessTaskDTO> paginatedTasks =
        processTaskRepository.findByDeletedNot((int) 1, PageRequest.of(page, size));

    if (paginatedTasks.isEmpty()) {

      return ResponseEntity.noContent().build(); // Return 204 if no tasks found
    }

    return ResponseEntity.ok(paginatedTasks.getContent()); // Return 200 with list of tasks
  }

  @Override
  public ResponseEntity<ProcessTaskDTO> updateProcessTask(Integer ptid, ProcessTaskDTO updatedProcessTask) {


    ProcessTaskDTO existingTask = processTaskRepository.findById(ptid).orElse(null);

    if (existingTask == null) {

      return ResponseEntity.notFound().build();
    }

    if (existingTask.getDeleted() == 1) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    BeanUtils.copyProperties(updatedProcessTask, existingTask, getNullPropertyNames(updatedProcessTask));
    existingTask.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());

    ProcessTaskDTO savedTask = processTaskRepository.save(existingTask);


    return ResponseEntity.ok(savedTask);
  }

  // Ignore null properties during BeanUtils.copyProperties
  private String[] getNullPropertyNames(ProcessTaskDTO source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set<String> nullPropertyNames = new HashSet<>();

    for (java.beans.PropertyDescriptor pd : pds) {
      if (src.getPropertyValue(pd.getName()) == null) {
        nullPropertyNames.add(pd.getName());
      }
    }

    return nullPropertyNames.toArray(new String[0]);
  }


  @Override
  public ResponseEntity<List<ProcessTaskDTO>> getByProcessId(Integer id, Integer page, Integer size) {
    Page<ProcessTaskDTO> paginatedTasks =
            processTaskRepository.findByProcessIdAndDeleted(id, 0, PageRequest.of(page, size));

    if (paginatedTasks.isEmpty()) {
      return ResponseEntity.noContent().build(); // 204
    }

    return ResponseEntity.ok(paginatedTasks.getContent()); // 200
  }
}