package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.entity.ProcessTask;

public interface ProcessTaskService {
    ProcessTask findProcessTaskByProcessIdAndOrder(int processId, int orderBy);

}
