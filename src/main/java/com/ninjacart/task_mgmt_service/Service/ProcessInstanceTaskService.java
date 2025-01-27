package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.entity.ProcessInstanceTask;
import com.ninjacart.task_mgmt_service.entity.WorkFlowPayLoad;

import java.util.Date;
import java.util.List;

public interface ProcessInstanceTaskService {
    List<ProcessInstanceTask> getProcessInstanceTasksByProcessInstanceIds(List<Integer> processInstanceIds);
    ProcessInstanceTask createProcessInstanceTaskForProcessInstance(int userId, WorkFlowPayLoad workFlowPayLoad, Integer processInstanceId, int processId, int orderBy, int assignedTo, Date createdAt) throws CyborgException;
    List<ProcessInstanceTask> updateProcessInstanceTasks(List<ProcessInstanceTask> processInstanceTasks);

}
