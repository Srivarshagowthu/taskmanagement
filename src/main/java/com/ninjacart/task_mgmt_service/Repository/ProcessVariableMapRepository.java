package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.ProcessVariableMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ProcessVariableMapRepository extends JpaRepository<ProcessVariableMap, Integer>,
        ProcessVariableMapRepositoryCustom {

    List<ProcessVariableMap> findByProcessIdAndDeleted(int processId, byte deleted);
}
