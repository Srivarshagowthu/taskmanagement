package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.dto.Process;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {
  List<Process> findByIdInAndDeleted(List<Integer> ids, byte deleted);

  Page<Process> findAllByDeletedFalse(
      Pageable pageable); // Find all processes with 'deleted' flag as false

  List<Process> findByIdInAndDeletedFalse(List<Integer> processIds);

  Process findByIdAndDeletedFalse(Integer id);

  Page<Process> findAll(Pageable pageable);

  Page<Process> findByDeletedNot(int deletedStatus, Pageable pageable);

  Process findByNameAndDeletedFalse(String name);
}
