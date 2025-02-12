package com.example.demo;

import com.example.demo.dto1.ProcessInstanceTaskDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
// Repository for ProcessInstanceTask
public interface ProcessInstanceTaskRepository extends JpaRepository<ProcessInstanceTaskDTO, Integer> {

    // Custom query method to find tasks by ProcessInstanceId
    List<ProcessInstanceTaskDTO> findByProcessInstanceId(Integer processInstanceId);

    // You can add more custom query methods as required
    // Example: Find tasks assigned to a specific agent

    // Example: Find tasks created after a specific date
    List<ProcessInstanceTaskDTO> findByCreationDateAfter(java.util.Date date);

    // Additional methods as per your application's need
}
