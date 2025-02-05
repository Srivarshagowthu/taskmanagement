package com.sky.cloud.delegate;

import com.sky.cloud.api.ApiUtil;
import com.sky.cloud.api.ProcessTasksApiDelegate;

import com.sky.cloud.dto.ProcessTaskDTO;
import com.sky.cloud.repository.ProcessTaskRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessTasksApiDelegateImpl implements ProcessTasksApiDelegate {

    private  final ProcessTaskRepository processTaskRepository;
    @Autowired
    public ProcessTasksApiDelegateImpl(ProcessTaskRepository processRepository) {
        this.processTaskRepository = processRepository;
    }

    @Override
    public ResponseEntity<ProcessTaskDTO> getProcessTaskById(Integer ptid) {

        ProcessTaskDTO processTaskDTO = processTaskRepository.findByIdAndDeleted(ptid, (int) 0);

        if (processTaskDTO != null) {
            return ResponseEntity.ok(processTaskDTO);  // Return 200 with task data
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if task not found or deleted
        }
    }

    @Override
    public ResponseEntity<List<ProcessTaskDTO>> createProcessTasks(List<ProcessTaskDTO> processDTOs) {

        if (processDTOs == null || processDTOs.isEmpty()) {
            return ResponseEntity.badRequest().build();  // Return 400 if input is invalid
        }

        List<ProcessTaskDTO> savedProcessDTOs = new ArrayList<>();
        for (ProcessTaskDTO processDTO : processDTOs) {
            if (processDTO.getName() == null || processDTO.getDescription() == null) {
                return ResponseEntity.badRequest().build();  // Return 400 if name or description is missing
            }

            // Set 'createdAt' only if it's not already set (for new entries)
            if (processDTO.getCreatedAt() == null) {
                processDTO.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());  // Set createdAt to current time in IST
            }

            // Always set 'updatedAt' to the current time in IST
            processDTO.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());  // Set updatedAt to current time in IST

            savedProcessDTOs.add(processTaskRepository.save(processDTO));  // Save each process task
        }

        return ResponseEntity.status(201).body(savedProcessDTOs);  // Return 201 Created with saved tasks
    }


    @Override
    public ResponseEntity<String> deleteProcessTaskById(Integer ptid) {
        return processTaskRepository.findById(ptid)
                .map(task -> {
                    task.setDeleted(1);  // Mark as deleted
                    processTaskRepository.save(task);  // Save the updated task with deleted flag
                    return ResponseEntity.ok("Task deleted successfully");  // Return success message
                })
                .orElseGet(() -> ResponseEntity.notFound().build());  // Return 404 if task not found
    }
    @Override
    public ResponseEntity<List<ProcessTaskDTO>> getAllProcessTasks(Integer page, Integer size) {


        // Fetch paginated results from the repository
        // Fetch paginated results where deleted flag is not 1 (not deleted)
        Page<ProcessTaskDTO> paginatedTasks = processTaskRepository.findByDeletedNot((int) 1, PageRequest.of(page, size));


        if (paginatedTasks.isEmpty()) {
            return ResponseEntity.noContent().build();  // Return 204 if no tasks found
        }
        return ResponseEntity.ok(paginatedTasks.getContent());  // Return 200 with list of tasks
    }
//    @Override
//    public ResponseEntity<ProcessTaskDTO> updateProcessTask(Integer ptid, ProcessTaskDTO updatedProcessTask) {
//
//        ProcessTaskDTO existingTask = processTaskRepository.findByIdAndDeleted(ptid, (byte) 0);
//
//        if (existingTask != null) {
//            existingTask.setName(updatedProcessTask.getName());  // Update name
//            existingTask.setDescription(updatedProcessTask.getDescription());  // Update description
//            existingTask.setDefaultPriority(updatedProcessTask.getDefaultPriority());  // Update defaultPriority
//            existingTask.setMinutesToAssign(updatedProcessTask.getMinutesToAssign());  // Update minutesToAssign
//            existingTask.setRequestParam(updatedProcessTask.getRequestParam());  // Update requestParam
//            existingTask.setUserId(updatedProcessTask.getUserId());  // Update userId
//            existingTask.setFilter(updatedProcessTask.getFilter());  // Update filter
//            existingTask.setMinutesToComplete(updatedProcessTask.getMinutesToComplete());  // Update minutesToComplete
//            existingTask.setCreatedBy(updatedProcessTask.getCreatedBy());  // Update createdBy
//            existingTask.setTaskId(updatedProcessTask.getTaskId());  // Update taskId
//
//            // Do not change the 'createdAt' if it's already set; only update 'updatedAt'
//            // Update 'updatedAt' to current time in IST
//            existingTask.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toOffsetDateTime());  // Update updatedAt to current time in IST
//
//            // Save the updated task to the repository
//            ProcessTaskDTO savedTask = processTaskRepository.save(existingTask);
//
//            return ResponseEntity.ok(savedTask);  // Return 200 with updated task data
//        } else {
//            return ResponseEntity.notFound().build();  // Return 404 if task not found or deleted
//        }
//    }




}