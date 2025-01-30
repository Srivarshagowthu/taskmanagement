package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.ProcessTaskRepository;
import com.ninjacart.task_mgmt_service.entity.ProcessTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessTaskServiceImpl implements ProcessTaskService{
    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Override
    public ProcessTask findProcessTaskByProcessIdAndOrder(int processId, int orderBy) {
        return processTaskRepository.findByProcessIdAndOrderAndDeleted(processId, orderBy, (byte)0);
    }
}
