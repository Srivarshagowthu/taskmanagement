package com.sky.cloud.repository;

import com.sky.cloud.dto1.ProcessInstanceDTO;
import com.sky.cloud.dto1.ProcessInstanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessInstanceServiceRepository extends JpaRepository<ProcessInstanceDTO, Integer> {

    // Custom query to get distinct statuses

    Optional<ProcessInstanceDTO> findByProcessInstanceId(Integer processInstanceId);

    // Assuming ProcessInstanceStatus is an enum or another entity that stores the status information


}
