package com.sky.cloud.impl;


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
import java.beans.PropertyDescriptor;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class ProcessTaskServiceImpl implements ProcessTaskService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Override
    public ResponseEntity<ProcessTaskDTO> getProcessTaskById(Integer ptid) {
        log.debug("Fetching process task with ID: {}", ptid);
        ProcessTaskDTO processTaskDTO = processTaskRepository.findByIdAndDeleted(ptid, 0);

        return processTaskDTO != null
                ? ResponseEntity.ok(processTaskDTO)
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<ProcessTaskDTO>> createProcessTasks(List<ProcessTaskDTO> processDTOs) {
        log.debug("Creating process tasks: {}", processDTOs);

        if (processDTOs == null || processDTOs.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<ProcessTaskDTO> validProcessTasks = new ArrayList<>();
        for (ProcessTaskDTO processDTO : processDTOs) {
            if (processDTO.getName() == null || processDTO.getDescription() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            processDTO.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
            processDTO.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
            processDTO.setDeleted(0);

            String lockKey = "Process Task Lock " + processDTO.getTaskId();
            RLock rLock = redissonClient.getFairLock(lockKey);
            try {
                if (rLock.tryLock(5, 10, TimeUnit.SECONDS)) {
                    try {
                        validProcessTasks.add(processDTO);
                    } finally {
                        rLock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Error acquiring lock for process task ID: {}", processDTO.getTaskId(), e);
            }
        }
        List<ProcessTaskDTO> savedProcessDTOs = processTaskRepository.saveAll(validProcessTasks);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProcessDTOs);
    }

    @Override
    public ResponseEntity<String> deleteProcessTaskById(Integer ptid) {
        return processTaskRepository.findById(ptid)
                .map(task -> {
                    task.setDeleted(1);
                    processTaskRepository.save(task);
                    return ResponseEntity.ok("Task deleted successfully");
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<ProcessTaskDTO>> getAllProcessTasks(Integer page, Integer size) {
        Page<ProcessTaskDTO> paginatedTasks = processTaskRepository.findByDeletedNot(1, PageRequest.of(page, size));

        return paginatedTasks.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(paginatedTasks.getContent());
    }

    @Override
    public ResponseEntity<ProcessTaskDTO> updateProcessTask(Integer ptid, ProcessTaskDTO updatedProcessTask) {
        ProcessTaskDTO existingTask = processTaskRepository.findById(ptid).orElse(null);

        if (existingTask == null || existingTask.getDeleted() == 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        BeanUtils.copyProperties(updatedProcessTask, existingTask, getNullPropertyNames(updatedProcessTask));
        existingTask.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        ProcessTaskDTO savedTask = processTaskRepository.save(existingTask);

        return ResponseEntity.ok(savedTask);
    }

    private String[] getNullPropertyNames(ProcessTaskDTO source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> nullPropertyNames = new HashSet<>();
        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(pd.getName()) == null) {
                nullPropertyNames.add(pd.getName());
            }
        }
        return nullPropertyNames.toArray(new String[0]);
    }

    @Override
    public ResponseEntity<List<ProcessTaskDTO>> getByProcessId(Integer id, Integer page, Integer size) {
        Page<ProcessTaskDTO> paginatedTasks = processTaskRepository.findByProcessIdAndDeleted(id, 0, PageRequest.of(page, size));

        return paginatedTasks.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(paginatedTasks.getContent());
    }
}
