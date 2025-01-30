package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceTaskCommentsRepository;
import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceTaskCommentsService;
import com.ninjacart.task_mgmt_service.entity.WorkFlowPayLoad;
import com.ninjacart.task_mgmt_service.model.ProcessInstanceTaskComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstanceTaskCommentsServiceimpl implements ProcessInstanceTaskCommentsService {
    @Autowired
    private ProcessInstanceTaskCommentsRepository processInstanceTaskCommentsRepository;

    @Override
    public ProcessInstanceTaskComments createProcessInstanceTaskCommentForPIT(int userId, int processInstanceTaskId, WorkFlowPayLoad workFlowPayLoad) {
        String comment = workFlowPayLoad.getComment();
        Integer classificationId = workFlowPayLoad.getClassificationId();
        if((comment == null || comment.isEmpty()) && classificationId == null) {
            return null;
        }
        return createProcessInstanceTaskComment(userId, processInstanceTaskId, comment, classificationId);
    }
    @Override
    public ProcessInstanceTaskComments createProcessInstanceTaskComment(int userId, int processInstanceTaskId, String comment, Integer classificationId) {

        ProcessInstanceTaskComments processInstanceTaskComments = new ProcessInstanceTaskComments();
        processInstanceTaskComments.setProcessInstanceTaskId(processInstanceTaskId);
        if(comment != null && !comment.isEmpty()) {
            processInstanceTaskComments.setComment(comment);
        }
        if (classificationId != null && classificationId != 0) {
            processInstanceTaskComments.setClassificationId(classificationId);
        }
        processInstanceTaskComments.setDeleted((byte) 0);
        processInstanceTaskCommentsRepository.saveAndFlush(processInstanceTaskComments);
        return processInstanceTaskComments;
    }

}
