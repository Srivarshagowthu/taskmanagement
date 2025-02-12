package com.sky.cloud.delegate;

import com.sky.cloud.api.ProcessTasksApiDelegate;

import com.sky.cloud.dto.ProcessInstanceTaskDTO;
import com.sky.cloud.dto.ProcessTaskDTO;

import com.sky.cloud.repository.ProcessTaskRepository;
import com.sky.cloud.service.ProcessTaskService;
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

import java.beans.PropertyDescriptor;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
@Service
@Slf4j
public class ProcessTasksApiDelegateImpl implements ProcessTasksApiDelegate {

  @Autowired
  private ProcessTaskService processTaskService;
  @Autowired
  private ProcessTaskRepository processTaskRepository;
  @Override
  public ResponseEntity<ProcessTaskDTO> getProcessTaskById(Integer ptid) {
    log.debug("Fetching process task with ID: {}", ptid);
    return processTaskService.getProcessTaskById(ptid);
  }

  @Override
  public ResponseEntity<List<ProcessTaskDTO>> createProcessTasks(@RequestBody List<ProcessTaskDTO> processDTOs) {
    log.debug("Creating process tasks: {}", processDTOs);
    return processTaskService.createProcessTasks(processDTOs);
  }

  @Override
  public ResponseEntity<String> deleteProcessTaskById(Integer ptid) {
    log.debug("Deleting process task with ID: {}", ptid);
    return processTaskService.deleteProcessTaskById(ptid);
  }

  @Override
  public ResponseEntity<List<ProcessTaskDTO>> getAllProcessTasks(Integer page, Integer size) {
    log.debug("Fetching all process tasks, page: {}, size: {}", page, size);
    return processTaskService.getAllProcessTasks(page, size);
  }

  @Override
  public ResponseEntity<ProcessTaskDTO> updateProcessTask(Integer ptid, ProcessTaskDTO updatedProcessTask) {
    log.debug("Updating process task with ID: {}", ptid);
    return processTaskService.updateProcessTask(ptid, updatedProcessTask);
  }

  @Override
  public ResponseEntity<List<ProcessTaskDTO>> getByProcessId(Integer id, Integer page, Integer size) {
    log.debug("Fetching process tasks for process ID: {}, page: {}, size: {}", id, page, size);
    return processTaskService.getByProcessId(id, page, size);
  }
}