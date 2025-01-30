package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.TeamProcessTaskRepository;
import com.ninjacart.task_mgmt_service.entity.TeamProcessTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class TeamProcessTaskServiceImpl implements TeamProcessTaskService{
    @Autowired
    private TeamProcessTaskRepository teamProcessTaskRepository;
    @Override
    public List<TeamProcessTask> getTeamProcessTaskForRef(Integer processTaskId, String ticketType , Integer refId, String refType) {
        List<TeamProcessTask> teamProcessTaskList = teamProcessTaskRepository.findAllByProcessTaskIdAndTicketTypeAndRefIdAndRefType(processTaskId,ticketType, refId, refType);
        return teamProcessTaskList;
    }
}
