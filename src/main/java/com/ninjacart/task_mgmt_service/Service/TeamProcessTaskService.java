package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.model.TeamProcessTaskDTO;
import com.ninjacart.task_mgmt_service.model.User;

import java.util.List;

public interface TeamProcessTaskService {
    List<TeamProcessTaskDTO> getAllTeamProcessTaskDto(User user, Integer teamId);
    TeamProcessTaskDTO getTeamProcessTaskForFilter(Integer processTaskId,Integer type,Integer refId, String refType);
    List<TeamProcessTaskDTO> getTeamProcessTaskForTaskAndTypeId(Integer processTaskId, Integer type);

}
