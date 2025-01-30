package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.TeamProcessTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamProcessTaskRepository extends JpaRepository<TeamProcessTask,Integer> {
    List<TeamProcessTask> findAllByProcessTaskIdAndTicketTypeAndRefIdAndRefType(Integer processTaskId, String ticketType, Integer refId, String refType);


}
