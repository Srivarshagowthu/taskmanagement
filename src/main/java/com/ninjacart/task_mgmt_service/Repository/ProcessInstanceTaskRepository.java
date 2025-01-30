package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.ProcessInstanceTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessInstanceTaskRepository extends JpaRepository<ProcessInstanceTask, Integer> {

    List<ProcessInstanceTask> findByProcessInstanceIdInAndDeleted(List<Integer> processInstanceIds, byte deleted);


}
