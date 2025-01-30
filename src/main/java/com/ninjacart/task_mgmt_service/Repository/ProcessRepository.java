package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.Process;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {

    // Find by ID without considering the 'deleted' flag (already covered in other methods)

    // Find by name
    //Process findByName(String name);

    // Find processes by a list of IDs and their 'deleted' flag status
    List<Process> findByIdInAndDeleted(List<Integer> ids, byte deleted);

    // Find processes by a list of names and their 'deleted' flag status
    List<Process> findByNameInAndDeletedFalse(List<String> processNames);

    // Paginated list of all processes
    Page<Process> findAll(Pageable pageable);
    Process findByNameAndDeletedFalse(String name);

    // Find all processes by their 'deleted' flag status (useful for filtering deleted or non-deleted processes)
    List<Process> findByDeleted(byte deleted);

    // Find a process by its ID and ensure it is not deleted (added check for 'deleted' flag)
    Process findByIdAndDeletedFalse(Integer id);

    // Find all processes that are not deleted
    Page<Process> findAllByDeletedFalse(Pageable pageable);  // Find all processes with 'deleted' flag as false

    List<Process> findByIdInAndDeletedFalse(List<Integer> processIds);
}
