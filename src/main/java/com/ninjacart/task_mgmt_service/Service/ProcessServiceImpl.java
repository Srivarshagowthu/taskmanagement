package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.ProcessRepository;
import com.ninjacart.task_mgmt_service.model.ProcessDTOV2;
import com.ninjacart.task_mgmt_service.model.enums.ProcessEnum;
import org.springframework.beans.factory.annotation.Autowired;
import com.ninjacart.task_mgmt_service.entity.Process;
import org.springframework.data.domain.Pageable;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.Exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    // Get by id
    @Override
    public Process getProcessById(Integer id) {
        log.info("Fetching process with ID: {}", id);
        return processRepository.findByIdAndDeletedFalse(id);  // Ensure deleted=false for fetching
    }

    // By name
    @Override
    public Process getProcessByName(String name) {
        log.info("Fetching process with name: {}", name);
        return processRepository.findByNameAndDeletedFalse(name);  // Ensure deleted=false for fetching
    }
    //applied pagination to get all processes for efficieny
    @Override
    public List<Process> getAllProcesses(Pageable pageable) {
        log.info("Fetching all processes with pagination");
        Page<Process> processPage = processRepository.findAllByDeletedFalse(pageable); // Ensure deleted=false
        return processPage.getContent(); // Get the content (list of processes) from the Page
    }

    @Override
    public List<Process> getProcessesByIds(List<Integer> processIds) {
        log.info("Fetching processes w ith IDs: {}", processIds);
        return processRepository.findByIdInAndDeletedFalse(processIds); // Ensure deleted=false for fetching
    }

    @Override
    public List<Process> getProcessesByNames(List<String> processNames) {
        log.info("Fetching processes with names: {}", processNames);
        return processRepository.findByNameInAndDeletedFalse(processNames);  // Ensure deleted=false for fetching
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


    //used stream,.tolist() for immutability
    @Override
    public List<ProcessDTOV2> getProcessList() {
        return Arrays.stream(ProcessEnum.values())
                .map(processEnum -> ProcessDTOV2.builder()
                        .id(processEnum.getCode())
                        .name(processEnum.name())
                        .allowUpdate(processEnum == ProcessEnum.CUSTOMER_MISSED_CALL)
                        .build())
                .toList(); // Efficiently collects the stream into an immutable list
    }

    // Soft delete process by ID i.e,will not del from db but we cant fetch its id after deletion implemented using deletd bit=true
    @Override
    public void softDelete(Integer id) throws CyborgException {
        log.info("Soft deleting process with ID: {}", id);

        // Check if process exists
        processRepository.findById(id).ifPresentOrElse(process -> {
            process.setDeleted((byte) 1);  // Set the 'deleted' flag to 1 (soft delete)
            processRepository.save(process);
            log.info("Process with ID: {} soft deleted successfully", id);
        }, () -> {
            log.error("Process with ID: {} not found", id);

        });
    }
    }
