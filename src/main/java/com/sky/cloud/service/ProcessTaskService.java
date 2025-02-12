package com.sky.cloud.service;

import com.sky.cloud.dto.ProcessTaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProcessTaskService {
    ResponseEntity<ProcessTaskDTO> getProcessTaskById(Integer ptid);
    ResponseEntity<List<ProcessTaskDTO>> createProcessTasks(List<ProcessTaskDTO> processDTOs);
    ResponseEntity<String> deleteProcessTaskById(Integer ptid);
    ResponseEntity<List<ProcessTaskDTO>> getAllProcessTasks(Integer page, Integer size);
    ResponseEntity<ProcessTaskDTO> updateProcessTask(Integer ptid, ProcessTaskDTO updatedProcessTask);
    ResponseEntity<List<ProcessTaskDTO>> getByProcessId(Integer id, Integer page, Integer size);
}