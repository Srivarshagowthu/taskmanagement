package com.ninjacart.task_mgmt_service.controller;

import com.ninjacart.task_mgmt_service.Service.ProcessService;
import com.ninjacart.task_mgmt_service.model.ApiResponse;
import com.ninjacart.task_mgmt_service.model.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ninjacart.task_mgmt_service.entity.Process;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/process")
@Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
@Slf4j
public class ProcessController {

    @Autowired
    private ProcessService processService;

    // Get process by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Process>> getProcessById(@PathVariable Integer id) {
        log.info("Fetching process with ID: {}", id);
        Optional<Process> process = processService.getProcessById(id);
        if (process.isEmpty()) {
            log.warn("Process with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Process found with ID: {}", id);
        return ResponseEntity.ok(process);
    }

    // Get process by Name
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

    // Fetch all processes
    @GetMapping("/all")
    public ResponseEntity<List<Process>> getAllProcesses() {
        log.info("Fetching all processes");
        List<Process> processes = processService.getAllProcesses();
        if (processes.isEmpty()) {
            log.warn("No processes found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Returns 204 if no processes are found
        }
        log.info("Fetched {} processes", processes.size());
        return ResponseEntity.ok(processes);  // Returns the list with 200 OK status
    }

    /*@GetMapping("/task/{id}")
    public ProcessTask getProcessTaskById(@PathVariable("id") int id) {
        log.info("Fetching process task with ID: {}", id);
        ProcessTask processTask = processService.getProcessTaskById(id);
        return processTask;
    }*/

    // Create new processes
    @PostMapping
    public ApiResponse createProcesses(@RequestBody List<Process> processes) throws CyborgException {
        log.info("Creating processes: {}", processes.size());
        ApiResponse response = ResponseUtil.jsonResponse(processService.createProcesses(processes));
        log.info("Created {} processes successfully", processes.size());
        return response;
    }

    // Batch update processes
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

    /*@GetMapping("/list")
    public List<ProcessDTOV2> getProcessList() {
        log.info("Fetching process list");
        return processService.getProcessList();
    }*/
}
