package com.ninjacart.task_mgmt_service.controller;

import com.ninjacart.task_mgmt_service.Service.ProcessService;
import com.ninjacart.task_mgmt_service.model.ApiResponse;
import com.ninjacart.task_mgmt_service.model.ProcessDTOV2;
import com.ninjacart.task_mgmt_service.model.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ninjacart.task_mgmt_service.entity.Process;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * ProcessController manages process-related operations
 */
@RestController
@RequestMapping("/process")
@Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
@Slf4j
public class ProcessController {

    @Autowired
    private ProcessService processService;

    /**
     * GET /process/id/{id} : Get a process by its ID
     *
     * @param id (required) - Process ID
     * @return Process details if found (status code 200), otherwise 404
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Process> getProcessById(@PathVariable Integer id) {
        log.info("Fetching process with ID: {}", id);
        Process process = processService.getProcessById(id);
        if (process == null) {
            log.warn("Process with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Process found with ID: {}", id);
        return ResponseEntity.ok(process);
    }

    /**
     * GET /process/name/{name} : Get a process by its name
     *
     * @param name (required) - Process name
     * @return Process details if found (status code 200), otherwise 404
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Process> getProcessByName(@PathVariable String name) {
        log.info("Fetching process with name: {}", name);
        Process process = processService.getProcessByName(name);
        if (process == null) {
            log.warn("Process with name {} not found", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Process found with name: {}", name);
        return ResponseEntity.ok(process);
    }

    /**
     * GET /process/all?page=x&size=x : Fetch all processes with pagination
     *
     * @param page (optional) - Page number (default 0)
     * @param size (optional) - Number of items per page (default 10)
     * @return List of processes (status code 200)
     */
    @GetMapping("/all")
    public List<Process> getAllProcesses(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return processService.getAllProcesses(pageable);
    }

    /**
     * POST /process : Create new processes
     *
     * @param processes (required) - List of processes to create
     * @return API response containing created processes (status code 200)
     * @throws CyborgException if an error occurs
     */
    @PostMapping
    public ApiResponse createProcesses(@RequestBody List<Process> processes) throws CyborgException {
        log.info("Creating processes: {}", processes.size());
        ApiResponse response = ResponseUtil.jsonResponse(processService.createProcesses(processes));
        log.info("Created {} processes successfully", processes.size());
        return response;
    }

    /**
     * PUT /process/batchUpdate : Batch update processes
     *
     * @param processes (required) - List of processes to update
     * @return Updated list of processes (status code 200), or 204 if no updates
     * @throws CyborgException if an error occurs
     */
    @PutMapping("/batchUpdate")
    public ResponseEntity<List<Process>> updateProcesses(@RequestBody List<Process> processes) throws CyborgException {
        log.info("Updating processes, total to update: {}", processes.size());
        List<Process> updatedProcesses = processService.updateProcess(processes);
        if (updatedProcesses.isEmpty()) {
            log.warn("No processes updated");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        log.info("Updated {} processes successfully", updatedProcesses.size());
        return ResponseEntity.ok(updatedProcesses);
    }

    /**
     * DELETE /process/delete/{id} : Soft delete a process by ID
     *
     * @param id (required) - Process ID to delete
     * @return API response confirming deletion (status code 200) or error (404)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> softDelete(@PathVariable Integer id) {
        try {
            log.info("Request to soft delete process with ID: {}", id);
            processService.softDelete(id);
            ApiResponse response = ResponseUtil.jsonResponse("Process soft deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (CyborgException e) {
            log.error("Error during soft delete", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.jsonResponse("Error: Process not found"));
        }
    }

    /**
     * GET /process/list : Fetch the list of all process DTOs
     *
     * @return List of ProcessDTOV2 objects (status code 200)
     */
    @GetMapping("/list")
    public List<ProcessDTOV2> getProcessList() {
        log.info("Fetching process list");
        return processService.getProcessList();
    }
}
