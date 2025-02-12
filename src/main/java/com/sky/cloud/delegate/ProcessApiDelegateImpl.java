package com.sky.cloud.delegate;
import com.sky.cloud.apiv.ProcessApiDelegate;
import com.sky.cloud.dtov.ModelApiResponse;
import com.sky.cloud.service.ProcessService;
import com.sky.cloud.dtov.Process;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RestController
public class ProcessApiDelegateImpl implements ProcessApiDelegate {
    private final ProcessService processService;
    public ProcessApiDelegateImpl(ProcessService processService) {
        this.processService = processService;
    }

    @Override
    public ResponseEntity<ModelApiResponse> createProcesses(List<Process> processList) {
        // Handle business logic and exception handling
        ModelApiResponse response = processService.createProcesses(processList);

        // Return ResponseEntity, not just the response object
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<com.sky.cloud.dtov.Process>> getAllProcesses(Integer page, Integer size) {
        log.info("Fetching all processes with pagination: page {}, size {}", page, size);

        // Ensure page & size are not null or negative
        int pageNumber = (page == null || page < 0) ? 0 : page;
        int pageSize = (size == null || size <= 0) ? 10 : size;

        // Fetch processes with pagination
        Page<Process> processPage = processService.getAllProcesses(pageNumber, pageSize);

        if (processPage.isEmpty()) {
            log.warn("No processes found for page {} with size {}", pageNumber, pageSize);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(processPage.getContent());
    }

    @Override
    public ResponseEntity<com.sky.cloud.dtov.Process> getProcessById(Integer id) {
        Process process = processService.getProcessById(id).getBody();

        if (process == null) {
            log.warn("Process with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(process);
    }

    @Override
    public ResponseEntity<Process> getProcessByName(String name) {
        Process process = processService.getProcessByName(name).getBody();

        if (process == null) {
            log.warn("Process with name '{}' not found", name);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(process);
    }


    @Override
    public ResponseEntity<ModelApiResponse> softDelete(Integer id) {
        ModelApiResponse response = processService.softDelete(id).getBody();
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<List<Process>> updateProcesses(List<Process> processList) {
        List<Process> updatedProcesses = processService.updateProcesses(processList).getBody();
        return ResponseEntity.ok(updatedProcesses);
    }

}

