package com.product.borg.service;
import com.product.borg.helper.exception.CustomException;
import com.product.borg.model.ProcessTaskFilter;
import com.product.borg.persistance.entity.ProcessTask;

import java.util.List;
public interface ProcessTaskService {

    ProcessTask findProcessTaskById(int processTaskId);
    ProcessTask findProcessTaskByProcessIdAndOrder(int processId, int orderBy);
    List<ProcessTask> findProcessTaskByProcessId(int processId);
    List<ProcessTask> findByIds(List<Integer> ids);

    List<ProcessTask> getByFilter(ProcessTaskFilter processTaskFilter);

    List<ProcessTask> createProcessTasks(List<ProcessTask> processTasks) throws CustomException;

    List<ProcessTask> updateProcessTasks(List<ProcessTask> processTasks) throws CustomException;
    void deleteProcessTask(Integer processTaskId) throws CustomException ;
    List<ProcessTask> getAllProcessTasks();
}
