package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.ProcessInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessInstanceRepository extends JpaRepository<ProcessInstance, Integer>, ProcessInstanceRepositoryCustom {
    ProcessInstance findByIdAndDeleted(int id, byte deleted);
    List<ProcessInstance> findByIdInAndDeleted(List<Integer> ids, byte deleted);
}