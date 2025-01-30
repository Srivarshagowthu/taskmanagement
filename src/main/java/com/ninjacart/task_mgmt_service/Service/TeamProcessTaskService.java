package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.entity.TeamProcessTask;
import com.ninjacart.task_mgmt_service.model.TeamProcessTaskDTO;
import com.ninjacart.task_mgmt_service.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TeamProcessTaskService {
    //List<TeamProcessTaskDTO> getAllTeamProcessTaskDto(User user, Integer teamId);
    //TeamProcessTaskDTO getTeamProcessTaskForFilter(Integer processTaskId,Integer type,Integer refId, String refType);
    //List<TeamProcessTaskDTO> getTeamProcessTaskForTaskAndTypeId(Integer processTaskId, Integer type);
    List<TeamProcessTask> getTeamProcessTaskForRef(Integer id, String ticketType, Integer refId, String refType);

}
