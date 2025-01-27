package com.ninjacart.task_mgmt_service.Repository;


import com.ninjacart.task_mgmt_service.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Integer> {

    Optional<Process> findById(Integer id);
    Process findByName(String name);
    Process findByIdAndDeleted(Integer id, byte deleted);
    List<Process> findByIdInAndDeleted(List<Integer> ids, byte deleted);
    List<Process> findByNameInAndDeleted(List<String> processNames, byte deleted);
}
