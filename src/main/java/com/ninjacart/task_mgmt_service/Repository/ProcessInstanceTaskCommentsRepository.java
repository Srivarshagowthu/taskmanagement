package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.model.ProcessInstanceTaskComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessInstanceTaskCommentsRepository extends JpaRepository<ProcessInstanceTaskComments, Integer> {
}
