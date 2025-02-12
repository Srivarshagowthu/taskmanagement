package com.sky.cloud.impl;

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

import java.beans.PropertyDescriptor;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
public class ProcessInstanceTaskServiceImpl implements ProcessInstanceTaskService {

    @Autowired
    private ProcessInstanceTaskRepository processInstanceTaskRepository;

    @Override
    public ResponseEntity<ProcessInstanceTaskDTO> getProcessInstanceTaskById(Integer id) {
        log.debug("Fetching ProcessInstanceTask with id: {}", id);
        ProcessInstanceTaskDTO processInstanceTaskDTO = processInstanceTaskRepository.findByIdAndDeleted(id, (byte) 0);
        return processInstanceTaskDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(processInstanceTaskDTO);
    }

    @Override
    public ResponseEntity<List<ProcessInstanceTaskDTO>> getAllProcessInstanceTasks(Integer page, Integer size) {
        log.debug("Fetching all ProcessInstanceTasks with page: {}, size: {}", page, size);
        Page<ProcessInstanceTaskDTO> paginatedTasks = processInstanceTaskRepository.findByDeletedNot(1, PageRequest.of(page, size));
        return paginatedTasks.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(paginatedTasks.getContent());
    }

    @Override
    public ResponseEntity<ProcessInstanceTaskDTO> createProcessInstanceTask(ProcessInstanceTaskDTO processInstanceTaskDTO) {
        log.debug("Creating new ProcessInstanceTask: {}", processInstanceTaskDTO);
        if (processInstanceTaskDTO == null || processInstanceTaskDTO.getName() == null || processInstanceTaskDTO.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }
        processInstanceTaskDTO.setCreatedAt(OffsetDateTime.now(ZoneOffset.ofHoursMinutes(5, 30)));
        processInstanceTaskDTO.setUpdatedAt(OffsetDateTime.now(ZoneOffset.ofHoursMinutes(5, 30)));
        processInstanceTaskDTO.setDeleted(0);
        return ResponseEntity.status(HttpStatus.CREATED).body(processInstanceTaskRepository.save(processInstanceTaskDTO));
    }

    @Override
    public ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTask(Integer id, ProcessInstanceTaskDTO updatedProcessInstanceTask) {
        log.debug("Updating ProcessInstanceTask with id: {}", id);
        ProcessInstanceTaskDTO existingTask = processInstanceTaskRepository.findByIdAndDeleted(id, 0);
        if (existingTask == null) {
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(updatedProcessInstanceTask, existingTask, getNullPropertyNames(updatedProcessInstanceTask, "createdAt", "deleted"));
        existingTask.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());
        return ResponseEntity.ok(processInstanceTaskRepository.save(existingTask));
    }

    @Override
    public ResponseEntity<String> deleteProcessInstanceTaskById(Integer id) {
        log.debug("Deleting ProcessInstanceTask with id: {}", id);
        return processInstanceTaskRepository.findById(id)
                .map(task -> {
                    task.setDeleted(1);
                    processInstanceTaskRepository.save(task);
                    return ResponseEntity.ok("Task deleted successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTaskStatus(Integer id, ProcessInstanceTaskDTO processInstanceTaskDTO) {
        log.debug("Updating status of ProcessInstanceTask with id: {}", id);
        if (id == null || processInstanceTaskDTO.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }
        ProcessInstanceTaskDTO existingTask = processInstanceTaskRepository.findByIdAndDeleted(id, 0);
        if (existingTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        existingTask.setStatus(processInstanceTaskDTO.getStatus());
        return ResponseEntity.ok(processInstanceTaskRepository.save(existingTask));
    }
//vcode
    @Override
    public void save(ProcessInstanceTaskDTO task) {

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
}
