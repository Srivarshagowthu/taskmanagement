package com.example.demo;

import com.example.demo.Exceptions.ErrorCode;
import com.example.demo.dto.ModelApiResponse;
import com.example.demo.dto.Process;
import com.example.demo.Exceptions.BadRequestException;
import com.example.demo.Exceptions.CyborgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import com.example.demo.ProcessRepository;
@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    // Create new processes
    @Override
    public ModelApiResponse createProcesses(List<Process> processes) throws CyborgException {
        if (processes == null || processes.isEmpty()) {
            log.error("The process list is empty or null.");
            throw new BadRequestException("The process list is empty or null.");
        }

        // Fetch all existing processes from the database
        List<Process> existingProcesses = processRepository.findAll();

        // Extract IDs from existing processes
        List<Integer> existingProcessIds = existingProcesses.stream()
                .map(Process::getId)  // Extract the IDs
                .filter(Objects::nonNull)  // Ensure no null IDs
                .collect(Collectors.toList());  // Collect into a list

        // Check for duplicate IDs in the request body
        Set<Integer> uniqueIds = new HashSet<>();  // Using a Set to ensure uniqueness
        for (Process process : processes) {
            Integer processId = process.getId();

            // Check if the process ID is already in the database
            if (existingProcessIds.contains(processId)) {
                log.error("Duplicate process ID found in the database: {}", processId);
                throw new CyborgException(ErrorCode.valueOf("Process with ID " + processId + " already exists in the database."));
            }

            // Check if the process ID is already in the request body (duplicate within the list)
            if (!uniqueIds.add(processId)) {
                log.error("Duplicate process ID found in the request: {}", processId);
                throw new CyborgException(ErrorCode.valueOf("Process with ID " + processId + " is already in the request."));
            }
        }

        // Validate all processes
        if (processes.stream().anyMatch(process -> !validateProcess(process))) {
            log.error("One or more processes failed validation.");
            throw new BadRequestException("One or more processes failed validation.");
        }

        log.info("Starting to create {} processes.", processes.size());

        // Filter out existing processes and set default fields
        Instant nowInstant = Instant.now();
        OffsetDateTime nowOffsetDateTime = nowInstant.atOffset(ZoneOffset.UTC);  // Convert to OffsetDateTime

        List<Process> newProcesses = processes.stream()
                .peek(process -> {
                    process.setCreatedBy(1);  // Assuming user ID 1, adjust as necessary
                    process.setCreatedAt(nowOffsetDateTime);  // Use OffsetDateTime instead of Date
                    process.setUpdatedBy(1);
                    process.setUpdatedAt(nowOffsetDateTime);  // Use OffsetDateTime instead of Date
                    process.setDeleted(0);  // Not deleted
                })
                .collect(Collectors.toList());

        // Save new processes to the database
        List<Process> savedProcesses = processRepository.saveAll(newProcesses);
        log.info("{} processes created successfully.", savedProcesses.size());

        return new ModelApiResponse().message("Processes created successfully").status("success");
    }


    // Fetch all processes with pagination
    @Override
    public Page<Process> getAllProcesses(Integer page, Integer size) {
         Pageable pageable = PageRequest.of(page, size);
            return processRepository.findByDeletedNot(1, pageable); // Assuming this is the repository method
    }


        // Get a process by ID
    @Override
    public ResponseEntity<Process> getProcessById(Integer id) throws CyborgException{
        log.info("Fetching process with ID: {}", id);
        Process process = processRepository.findByIdAndDeletedFalse(id);
        if (process != null) {
            return ResponseEntity.ok(process);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get a process by name
    @Override
    public ResponseEntity<Process> getProcessByName(String name) {
        log.info("Fetching process with name: {}", name);
        Process process = processRepository.findByNameAndDeletedFalse(name);
        if (process != null) {
            return ResponseEntity.ok(process);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Soft delete a process by ID
    @Override
    @Transactional
    public ResponseEntity<ModelApiResponse> softDelete(Integer id) throws CyborgException {
        log.info("Soft deleting process with ID: {}", id);
        Process process = processRepository.findByIdAndDeletedFalse(id);
        if (process == null) {
            log.error("Process with ID: {} not found", id);
            return ResponseEntity.status(404).body(new ModelApiResponse().message("Process not found").status("error"));
        }
        process.setDeleted(1); // Mark as deleted
        processRepository.save(process);
        log.info("Process with ID: {} soft deleted successfully", id);
        return ResponseEntity.ok(new ModelApiResponse().message("Process soft deleted successfully").status("success"));
    }

    // Batch update processes
    @Override
    public ResponseEntity<List<Process>> updateProcesses(List<Process> processes) throws CyborgException {
        log.info("Updating {} processes.", processes.size());

        // Validate the processes and ensure each has a valid ID
        for (Process process : processes) {
            if (Objects.isNull(process.getId())) {
                log.error("Process ID cannot be null");
                throw new BadRequestException("Id cannot be null");
            }
        }

        // Fetch the existing processes from the database
        List<Process> existingProcesses = processRepository.findAllById(processes.stream()
                .map(Process::getId)
                .collect(Collectors.toList()));

        // Update existing processes with new data
        for (Process existingProcess : existingProcesses) {
            Process updatedProcess = processes.stream()
                    .filter(p -> p.getId().equals(existingProcess.getId()))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("Process not found"));
            // Check if 'active' field has changed
            if (!existingProcess.getActive().equals(updatedProcess.getActive())) {
                log.error("Attempt to modify 'active' field for process ID: {}", existingProcess.getId());
                throw new CyborgException(ErrorCode.valueOf("The 'active' field cannot be modified."));
            }

            // Check if 'version' field has changed
            if (!existingProcess.getVersion().equals(updatedProcess.getVersion())) {
                log.error("Attempt to modify 'version' field for process ID: {}", existingProcess.getId());
                throw new CyborgException(ErrorCode.valueOf("The 'version' field cannot be modified."));
            }

            existingProcess.setName(updatedProcess.getName());
            existingProcess.setUpdatedBy(1); // Assuming user ID 1, adjust as necessary
            existingProcess.setUpdatedAt(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneOffset.UTC));
            existingProcess.setDescription(updatedProcess.getDescription());

        }


        List<Process> updatedProcesses = processRepository.saveAll(existingProcesses);
        log.info("{} processes updated successfully.", updatedProcesses.size());
        return ResponseEntity.ok(updatedProcesses);
    }

    // Validate process using reflection or map
    private boolean validateProcess(Process process) {
        try {
            // List to track IDs encountered
            Set<Integer> seenIds = new HashSet<>();

            // Fields to exclude from null validation (auto-generated fields)
            Set<String> excludedFields = new HashSet<>(Arrays.asList("updatedAt", "createdBy", "updatedBy", "deleted", "createdAt"));

            for (Field field : Process.class.getDeclaredFields()) {
                field.setAccessible(true);

                // Skip auto-generated fields (updatedAt, createdBy, updatedBy, deleted, createdAt)
                if (excludedFields.contains(field.getName())) {
                    continue;
                }

                // Check for null fields (other than the excluded ones)
                if (Objects.isNull(field.get(process))) {
                    log.warn("Field {} is null in process validation", field.getName());
                    return false; // Return false if any non-optional field is null
                }

                // If the field is an ID field (or similar), check for uniqueness
                if (field.getName().equals("id")) { // Assuming "id" is the field name for unique IDs
                    Integer idValue = (Integer) field.get(process);

                    // If the ID is already in the set, it's a duplicate
                    if (seenIds.contains(idValue)) {
                        log.error("Duplicate ID found: {}", idValue);
                        return false; // Return false if duplicate ID is found
                    }

                    // Add the ID to the set of seen IDs
                    seenIds.add(idValue);
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Error during validation", e);
            return false; // Handle unexpected errors
        }

        return true; // Return true if all fields are valid in this validation
    }

}
