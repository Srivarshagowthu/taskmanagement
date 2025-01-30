package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.ProcessCoexistenceMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessCoexistenceMapRepository extends JpaRepository<ProcessCoexistenceMap, Integer> {

    List<ProcessCoexistenceMap> findByProcessIdAndDeleted(int processId, byte deleted);
}
