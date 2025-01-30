package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.ProcessTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessTaskRepository extends JpaRepository<ProcessTask, Integer>{
    ProcessTask findByProcessIdAndOrderAndDeleted(Integer processId, Integer order, byte deleted);

}
