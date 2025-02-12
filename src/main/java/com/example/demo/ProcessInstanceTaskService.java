package com.example.demo;

import com.example.demo.dto1.ProcessInstanceTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessInstanceTaskService {

    @Autowired
    private ProcessInstanceTaskRepository processInstanceTaskRepository;


    // Method to fetch all tasks associated with a process instance ID
    public List<ProcessInstanceTaskDTO> getTasksByProcessInstanceId(Integer processInstanceId) {
        // Fetch tasks from the repository based on processInstanceId
        List<ProcessInstanceTaskDTO> tasks = processInstanceTaskRepository.findByProcessInstanceId(processInstanceId);

        // Convert each ProcessInstanceTask entity to ProcessInstanceTaskDTO
        return tasks.stream()
                .map(task -> {
                    ProcessInstanceTaskDTO taskDTO = new ProcessInstanceTaskDTO();

                    // Set values using setters from the ProcessInstanceTask entity
                    taskDTO.setTaskId(task.getTaskId());  // Set Task ID
                    taskDTO.setTaskName(task.getTaskName());  // Replace 'task.getTaskName()' with the correct field
                    taskDTO.setAssignee(task.getAssignee());  // Set Assignee

                    return taskDTO;
                })
                .collect(Collectors.toList());
    }


    // Method to update a task
    public ProcessInstanceTaskDTO updateTask(ProcessInstanceTaskDTO task) {
        if (task.getTaskId() != null && processInstanceTaskRepository.existsById(task.getTaskId())) {
            return processInstanceTaskRepository.save(task);
        } else {
            // If task doesn't exist, return null or throw an exception
            return null;
        }
    }

    // Method to delete a task
    public void deleteTask(Integer taskId) {
        if (processInstanceTaskRepository.existsById(taskId)) {
            processInstanceTaskRepository.deleteById(taskId);
        }
    }
    public ProcessInstanceTaskDTO getTaskDetailsById(Integer taskId) {
        Optional<ProcessInstanceTaskDTO> taskOptional = processInstanceTaskRepository.findById(taskId);

        if (taskOptional.isPresent()) {
            ProcessInstanceTaskDTO task = taskOptional.get();
            ProcessInstanceTaskDTO taskDTO = new ProcessInstanceTaskDTO();

            // Set the values from the ProcessInstanceTask entity to the DTO
            taskDTO.setTaskId(Math.toIntExact(task.getTaskId()));  // Convert Long to Integer
            taskDTO.setTaskName("Task #" + task.getTaskName());  // Example: Replace with actual logic for taskName
            taskDTO.setAssignee("Agent " + task.getAssignee());  // Replace with actual logic for assignee

            taskDTO.setProcessInstanceId(task.getProcessInstanceId());
            taskDTO.setCreationDate(task.getCreationDate());
            return taskDTO;
        } else {
            // Handle the case where the task is not found
            throw new RuntimeException("Task not found with ID: " + taskId);
        }
    }

    public ProcessInstanceTaskDTO save(ProcessInstanceTaskDTO task) {
        // Validate if the task is valid
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        // Optionally, you can add more validation here, e.g., check if task has necessary fields

        // Save the task to the database using the repository
        ProcessInstanceTaskDTO savedTask = processInstanceTaskRepository.save(task);

        // Return the saved task, now it will contain the generated ID (if it's auto-generated)
        return savedTask;
    }

}
