package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.model.ProcessURLConfiguration;
import com.ninjacart.task_mgmt_service.model.TeamProcessTaskDTO;
import com.ninjacart.task_mgmt_service.model.User;
import com.ninjacart.task_mgmt_service.model.enums.TeamProcessTaskEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
@Slf4j
public class AutoAssignTaskHelper {
    @Autowired
    private TeamProcessTaskService teamProcessTaskService;
    @Autowired
    private ProcessURLConfiguration processURLConfiguration;
    public void autoAssign(User user, HttpServletRequest request, List<TeamProcessTaskEnum> teamProcessTaskEnumList, boolean forceAssign) throws Exception {
        // Check if the teamProcessTaskEnumList is null or empty
        if (teamProcessTaskEnumList == null || teamProcessTaskEnumList.isEmpty()) {
            log.info("No tasks found for auto assignment.");
            return;
        }

        // Prepare the HttpHeaders for the request
        HttpHeadersModel httpHeadersModel = HttpHeadersModel.builder().authorization(request.getHeader("Authorization")).build();

        // Iterate through the list of TeamProcessTaskEnum
        for (TeamProcessTaskEnum teamProcessTaskEnum : teamProcessTaskEnumList) {
            // Log the task being processed
            log.info("Processing Task Type: {}", teamProcessTaskEnum);

            // Log additional information if needed
            //log.info("User: {} | Force Assign: {}", User.getUsername(), forceAssign);

            // If needed, you can add custom handling or logic here
            // For example, simulating the assignment process
            log.info("Simulating auto-assign task: {}", teamProcessTaskEnum);

            // If you need to add any other logic, you can do it here. This code does not invoke autoAssignTaskService anymore.
        }
    }

    public void autoAssignV2(User user, HttpServletRequest request, Integer assignType , boolean forceAssign) throws Exception {
        HttpHeadersModel httpHeadersModel = HttpHeadersModel.builder().authorization(request.getHeader("Authorization")).build();
        List<TeamProcessTaskDTO> teamProcessTaskDTOListList = teamProcessTaskService.getAllTeamProcessTaskDto(user,assignType);
        Boolean isCustomAutoAssignEnable = processURLConfiguration.getBooleanProperty("enable.custom.auto.assign.tickets", true);
        log.info("execute custom assignment logic -->", isCustomAutoAssignEnable);
        if(!teamProcessTaskDTOListList.isEmpty() && isCustomAutoAssignEnable ){
            log.info("calling new auto assign flow for assignType, {}", assignType);
            for(TeamProcessTaskDTO teamProcessTaskDTO : teamProcessTaskDTOListList){
                log.info("Processing Task ID: {}", teamProcessTaskDTO.getId());
                // You can log any necessary details here regarding the task
                // Removed the call to autoAssignTaskService for your case
            }
        } else {
            log.info("Calling old auto-assign flow for assignType: {}", assignType);
            List<TeamProcessTaskEnum> teamProcessTaskEnumList = Collections.singletonList(TeamProcessTaskEnum.getTeamProcessTaskEnumByType(assignType));

            // Track the stage of processing without the autoAssignTaskService
            for (TeamProcessTaskEnum teamProcessTaskEnum : teamProcessTaskEnumList) {
                log.info("Processing Task Type: {}", teamProcessTaskEnum);
                // Log any other necessary information for the old flow
            }
        }
    }
}
