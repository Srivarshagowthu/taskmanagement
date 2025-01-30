package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.WorkFlowPayLoad;
import com.ninjacart.task_mgmt_service.model.ProcessInstanceTaskComments;

public interface ProcessInstanceTaskCommentsService {
    ProcessInstanceTaskComments createProcessInstanceTaskCommentForPIT(int userId, int processInstanceTaskId, WorkFlowPayLoad workFlowPayLoad);

    ProcessInstanceTaskComments createProcessInstanceTaskComment(int userId, int processInstanceTaskId, String comment, Integer classificationId);

}
