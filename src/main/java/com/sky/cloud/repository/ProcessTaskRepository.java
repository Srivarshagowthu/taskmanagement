package com.sky.cloud.repository;

import com.sky.cloud.dto.ProcessTaskDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProcessTaskRepository extends JpaRepository<ProcessTaskDTO, Integer> {
  Page<ProcessTaskDTO> findByDeletedNot(int deleted, Pageable pageable);

  ProcessTaskDTO findByIdAndDeleted(int id, int deleted);

  Page<ProcessTaskDTO> findByProcessIdAndDeleted(Integer processId, Integer deleted, Pageable pageable);
}
