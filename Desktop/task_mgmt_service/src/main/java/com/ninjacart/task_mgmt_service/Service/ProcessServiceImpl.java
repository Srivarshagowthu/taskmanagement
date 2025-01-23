package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.ProcessRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import com.ninjacart.task_mgmt_service.entity.Process;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.Exception.BadRequestException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    // Get by id
    @Override
    public Optional<Process> getProcessById(Integer id) {
        log.info("Fetching process with ID: {}", id);
        return processRepository.findById(id);
    }

    // By name
    @Override
    public Process getProcessByName(String name) {
        log.info("Fetching process with name: {}", name);
        return processRepository.findByName(name);
    }

    @Override
    public List<Process> getAllProcesses() {
        log.info("Fetching all processes");
        return processRepository.findAll();
    }

    @Override
    public List<Process> getProcessesByIds(List<Integer> processIds) {
        log.info("Fetching processes with IDs: {}", processIds);
        return processRepository.findByIdInAndDeleted(processIds, (byte) 0);
    }

    @Override
    public List<Process> getProcessesByNames(List<String> processNames) {
        log.info("Fetching processes with names: {}", processNames);
        return processRepository.findByNameInAndDeleted(processNames, (byte) 0);
    }

    @Override
    public List<Process> createProcesses(List<Process> processes) throws CyborgException {
        if (processes == null || processes.isEmpty()) {
            log.error("The process list is empty or null.");
            throw new BadRequestException("The process list is empty or null.");
        }

        // Validate all processes
        if (processes.stream().anyMatch(process -> !validateProcess(process))) {
            log.error("One or more processes failed validation.");
            throw new BadRequestException("One or more processes failed validation.");
        }

        log.info("Starting to create {} processes.", processes.size());

        // Using stream for optimization
        List<Integer> processIds = processes.stream()
                .map(Process::getId)
                .filter(Objects::nonNull)
                .toList();

        // Fetch existing processes from the database and map them by ID
        Map<Integer, Process> existingProcesses = getProcessesByIds(processIds).stream()
                .collect(Collectors.toMap(
                        Process::getId,
                        Function.identity(),
                        (existing, duplicate) -> existing // Handle duplicates (keep the first one)
                ));

        // Filter out existing processes and set default fields directly
        Instant now = Instant.now();
        List<Process> newProcesses = processes.stream()
                .filter(process -> !existingProcesses.containsKey(process.getId())) // Exclude existing IDs
                .peek(process -> {
                    // Set default fields inline
                    process.setCreatedBy(1);
                    process.setCreatedAt(Date.from(now));
                    process.setUpdatedBy(1);
                    process.setUpdatedAt(Date.from(now));
                    process.setDeleted((byte) 0);
                })
                .toList();

        if (newProcesses.isEmpty()) {
            log.error("Ids provided in the request body already exist.");
            throw new BadRequestException("Ids provided in the request body already exist.");
        }

        // Save and return the new processes
        log.info("{} processes created successfully.", newProcesses.size());
        return processRepository.saveAll(newProcesses);
    }

    @Override
    public List<Process> updateProcess(List<Process> processes) throws CyborgException {
        // Validate the processes and ensure each has a valid ID
        for (Process process : processes) {
            if (Objects.isNull(process.getId())) {
                log.error("Process ID cannot be null");
                throw new BadRequestException("Id cannot be null");
            }
        }

        log.info("Starting to update {} processes.", processes.size());

        // Create a map of incoming processes by their IDs
        Map<Integer, Process> processMap = processes.stream()
                .collect(Collectors.toMap(Process::getId, Function.identity(), (oldK, newK) -> oldK));

        // Fetch the existing processes from the database
        List<Process> existingProcesses = getProcessesByIds(
                processes.stream().map(Process::getId).collect(Collectors.toList()));

        // Check for consistency between incoming and existing processes
        if (existingProcesses.size() != processMap.size()) {
            log.error("Id mismatch or non-existing ID in request data");
            throw new BadRequestException("Id doesn't exist or mismatch in request data");
        }

        // Iterate over existing processes and update non-null fields using reflection
        for (Process existingProcess : existingProcesses) {
            Process updatedProcess = processMap.get(existingProcess.getId());
            // Iterate over all fields of the Process class
            for (Field field : Process.class.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    // Get value from the incoming process
                    Object newValue = field.get(updatedProcess);
                    if (newValue != null) {
                        // Set the new value to the existing process if it's not null
                        field.set(existingProcess, newValue);
                    }
                } catch (IllegalAccessException e) {
                    log.error("Failed to update field: {}", field.getName(), e);
                    throw new RuntimeException("Failed to update field " + field.getName(), e);
                }
            }

            // Update the timestamp fields
            existingProcess.setUpdatedBy(1);  // Assuming 1 is the current user for this context
            existingProcess.setUpdatedAt(new Date());
        }

        // Save all updated processes to the database
        log.info("{} processes updated successfully.", existingProcesses.size());
        return processRepository.saveAll(existingProcesses);
    }

    // Validation using reflection or map
    private boolean validateProcess(Process process) {
        try {
            for (Field field : Process.class.getDeclaredFields()) {
                field.setAccessible(true);
                if (Objects.isNull(field.get(process))) {
                    log.warn("Field {} is null in process validation", field.getName());
                    return false; // Return false if any field is null
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Error during validation", e);
            return false; // Handle unexpected errors
        }
        return true; // Return true if all fields are valid
    }

    // Other methods can be logged similarly as needed...
}
