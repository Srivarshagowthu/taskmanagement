package com.example.demo;
import com.example.demo.Exceptions.CyborgException;
import com.example.demo.ProcessService;
import com.example.demo.api.ProcessApiDelegate;
import com.example.demo.dto.ModelApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.dto.Process;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RestController
public class ProcessApiDelegateImpl implements ProcessApiDelegate {
    private final ProcessService processService;
    public ProcessApiDelegateImpl(ProcessService processService) {
        this.processService = processService;
    }

    @Override
    public ResponseEntity<ModelApiResponse> createProcesses(List<Process> processList) throws CyborgException {
        // Handle business logic and exception handling
        ModelApiResponse response = processService.createProcesses(processList);

        // Return ResponseEntity, not just the response object
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<List<Process>> getAllProcesses(Integer page, Integer size) {
        try {
            log.info("Fetching all processes with pagination: page {}, size {}", page, size);

            // Ensure page & size are not null or negative
            int pageNumber = (page == null || page < 0) ? 0 : page;
            int pageSize = (size == null || size <= 0) ? 10 : size;

            // Check if processService expects integers instead of Pageable
            Page<Process> processPage = processService.getAllProcesses(pageNumber, pageSize);

            if (processPage.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<Process> processList = processPage.getContent();


            return ResponseEntity.ok(processList);
        } catch (CyborgException e) {
            log.error("Error fetching processes: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error fetching processes: " + e.getMessage(), e);
        }
    }
    @Override
    public ResponseEntity<Process> getProcessById(Integer id) throws CyborgException{
        ResponseEntity<Process> process = processService.getProcessById(id);
        // Return the process response or 404 if not found
        return process != null ? process : ResponseEntity.notFound().build();
    }
    @Override
    public ResponseEntity<Process> getProcessByName(String name){
        // Fetch the process by name from the service layer
        Process process = processService.getProcessByName(name).getBody();

        // Return the process response or 404 if not found
        return process != null ? ResponseEntity.ok(process) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<ModelApiResponse> softDelete(Integer id) throws CyborgException {
        ModelApiResponse response = processService.softDelete(id).getBody();
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<List<Process>> updateProcesses(List<Process> processList) throws CyborgException{
        List<Process> updatedProcesses = processService.updateProcesses(processList).getBody();
        return ResponseEntity.ok(updatedProcesses);
    }

}

