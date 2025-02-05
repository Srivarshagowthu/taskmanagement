package com.sky.cloud.delegate;

import com.sky.cloud.api.ProcessInstanceTasksApiDelegate;
import com.sky.cloud.dto.ProcessInstanceTaskDTO;

import com.sky.cloud.repository.ProcessInstanceTaskRepository;
import com.sky.cloud.repository.ProcessTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
@Service
public class ProcessInstanceTaskApiDelegateImpl implements ProcessInstanceTasksApiDelegate {
    private ProcessInstanceTaskRepository processInstanceTaskRepository;

    @Autowired
    public ProcessInstanceTaskApiDelegateImpl(ProcessInstanceTaskRepository processInstanceTaskRepository) {
        this.processInstanceTaskRepository = processInstanceTaskRepository;
    }

    @Override
    public ResponseEntity<ProcessInstanceTaskDTO> getProcessInstanceTaskById(Integer id) {

        ProcessInstanceTaskDTO processInstanceTaskDTO = processInstanceTaskRepository.findByIdAndDeleted(id, (byte) 0);
        if (processInstanceTaskDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(processInstanceTaskDTO);
    }

    @Override
    public ResponseEntity<List<ProcessInstanceTaskDTO>> getAllProcessInstanceTasks(Integer page, Integer size) {

        Page<ProcessInstanceTaskDTO> paginatedTasks = processInstanceTaskRepository.findByDeletedNot((int) 1, PageRequest.of(page, size));


        if (paginatedTasks.isEmpty()) {
            return ResponseEntity.noContent().build();  // Return 204 if no tasks found
        }
        return ResponseEntity.ok(paginatedTasks.getContent());
    }

    @Override
    public ResponseEntity<ProcessInstanceTaskDTO> createProcessInstanceTasks(ProcessInstanceTaskDTO processInstanceTaskDTO) {
        if (processInstanceTaskDTO == null || processInstanceTaskDTO.getName() == null || processInstanceTaskDTO.getDescription() == null) {
            return ResponseEntity.badRequest().build();  // Return 400 if input is invalid
        }

        // Set 'createdAt' only if it's not already set (for new entries)
        if (processInstanceTaskDTO.getCreatedAt() == null) {
            processInstanceTaskDTO.setCreatedAt(OffsetDateTime.now(ZoneOffset.ofHoursMinutes(5, 30)));  // Set createdAt to current time (IST)
        }

        // Always set 'updatedAt' to the current time in IST
        processInstanceTaskDTO.setUpdatedAt(OffsetDateTime.now(ZoneOffset.ofHoursMinutes(5, 30)));  // Set updatedAt to current time (IST)

        // Save the task
        ProcessInstanceTaskDTO savedTask = processInstanceTaskRepository.save(processInstanceTaskDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);  // Return 201 Created with saved task
    }


    @Override
    public ResponseEntity<ProcessInstanceTaskDTO> updateProcessInstanceTask(Integer id, ProcessInstanceTaskDTO updatedProcessInstanceTask) {
        ProcessInstanceTaskDTO existingTask = processInstanceTaskRepository.findByIdAndDeleted(id, (byte) 0);

        if (existingTask != null) {
            existingTask.setProcessInstanceId(updatedProcessInstanceTask.getProcessInstanceId());  // Update processInstanceId
            existingTask.setProcessTaskId(updatedProcessInstanceTask.getProcessTaskId());  // Update processTaskId
            existingTask.setUserId(updatedProcessInstanceTask.getUserId());  // Update userId
            existingTask.setAssignedTo(updatedProcessInstanceTask.getAssignedTo());  // Update assignedTo
            existingTask.setNextAssignedTo(updatedProcessInstanceTask.getNextAssignedTo());  // Update nextAssignedTo
            existingTask.setAssignedAt(updatedProcessInstanceTask.getAssignedAt());  // Update assignedAt
            existingTask.setName(updatedProcessInstanceTask.getName());  // Update name
            existingTask.setDescription(updatedProcessInstanceTask.getDescription());  // Update description
            existingTask.setType(updatedProcessInstanceTask.getType());  // Update type
            existingTask.setPriority(updatedProcessInstanceTask.getPriority());  // Update priority
            existingTask.setStatus(updatedProcessInstanceTask.getStatus());  // Update status
            existingTask.setStartDate(updatedProcessInstanceTask.getStartDate());  // Update startDate
            existingTask.setDueDate(updatedProcessInstanceTask.getDueDate());  // Update dueDate
            existingTask.setEndDate(updatedProcessInstanceTask.getEndDate());  // Update endDate
            existingTask.setPostponedDate(updatedProcessInstanceTask.getPostponedDate());  // Update postponedDate
            existingTask.setTaskDate(updatedProcessInstanceTask.getTaskDate());  // Update taskDate
            existingTask.setLatitude(updatedProcessInstanceTask.getLatitude());  // Update latitude
            existingTask.setLongitude(updatedProcessInstanceTask.getLongitude());  // Update longitude
            // Don't update 'createdAt', leave it as is
            existingTask.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());  // Update updatedAt to current IST time
            existingTask.setCreatedBy(updatedProcessInstanceTask.getCreatedBy());  // Update createdBy
            existingTask.setUpdatedBy(updatedProcessInstanceTask.getUpdatedBy());  // Update updatedBy
            existingTask.setDeleted(updatedProcessInstanceTask.getDeleted());  // Update deleted

            ProcessInstanceTaskDTO savedTask = processInstanceTaskRepository.save(existingTask);

            return ResponseEntity.ok(savedTask);  // Return 200 with updated task data
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if task not found or deleted
        }
    }


    @Override
    public ResponseEntity<String> deleteProcessInstanceTaskById(Integer id) {
        return processInstanceTaskRepository.findById(id)
                .map(task -> {
                    task.setDeleted(1);  // Mark as deleted
                    processInstanceTaskRepository.save(task);  // Save the updated task with deleted flag
                    return ResponseEntity.ok("Task deleted successfully");  // Return success message
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
