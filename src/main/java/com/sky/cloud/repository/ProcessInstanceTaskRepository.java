package com.sky.cloud.repository;

import com.sky.cloud.dto.ProcessInstanceTaskDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public interface ProcessInstanceTaskRepository
    extends JpaRepository<ProcessInstanceTaskDTO, Integer> {

  ProcessInstanceTaskDTO findByIdAndDeleted(int id, int deleted);

  Page<ProcessInstanceTaskDTO> findByDeletedNot(int deleted, Pageable pageable);
 // void updateTicketStatus(int pitId, UpdateProcessInstanceTaskDTO updateProcessInstanceTaskDTO, String username);
//  SELECT * FROM process_instance_task WHERE id = ? AND deleted = ? LIMIT 1;

}
