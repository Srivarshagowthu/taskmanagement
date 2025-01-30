package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.ProcessInstanceVariable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessInstanceVariableRepository extends JpaRepository<ProcessInstanceVariable, Integer> {
}
