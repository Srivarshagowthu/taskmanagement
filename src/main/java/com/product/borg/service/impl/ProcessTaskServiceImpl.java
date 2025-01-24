package com.product.borg.service.impl;

import com.product.borg.helper.exception.CustomException;
import com.product.borg.helper.exception.errorcode.ErrorCode;
import com.product.borg.model.ProcessTaskFilter;
import com.product.borg.persistance.dao.ProcessTaskRepository;
import com.product.borg.persistance.entity.ProcessTask;
import com.product.borg.service.ProcessTaskService;
import com.product.borg.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProcessTaskServiceImpl implements ProcessTaskService {

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Autowired
    private MapperUtils mapperUtils;
    @Override
    public List<ProcessTask> getAllProcessTasks() {
        log.info("Fetching all process tasks");
        return processTaskRepository.findAll();
    }
    @Override
    public ProcessTask findProcessTaskByProcessIdAndOrder(int processId, int orderBy) {
        return processTaskRepository.findByProcessIdAndOrderAndDeleted(processId, orderBy, (byte)0);
    }

    @Override
    public List<ProcessTask> findProcessTaskByProcessId(int processId) {
        return processTaskRepository.findByProcessIdAndDeleted(processId, (byte)0);
    }

    @Override
    public ProcessTask findProcessTaskById(int processTaskId) {
        return processTaskRepository.findByIdAndDeleted(processTaskId, (byte)0);
    }

    @Override
    public List<ProcessTask> findByIds(List<Integer> ids) {
        return processTaskRepository.findAllById(ids);
    }

    @Override
    public List<ProcessTask> getByFilter(ProcessTaskFilter processTaskFilter) {
        return processTaskRepository.findByFilter(processTaskFilter);
    }

    @Override
    public List<ProcessTask> createProcessTasks(List<ProcessTask> processTasks)
            throws CustomException {
//        for (ProcessTask processTask : processTasks) {
//            validateProcessTask(processTask);
//        }
//        Integer maxId = processTaskRepository.findMaxId();
//        List<ProcessTask> existingProcessTasks = getByFilter(toProcessTaskFilter(processTasks));
//        Map<String, ProcessTask> processTaskMap = existingProcessTasks.stream().collect(
//                Collectors.toMap(each -> (each.getProcessId() + ":" + each.getTaskId()),
//                        Function.identity(), (oldK, newK) -> oldK));
//        List<ProcessTask> createEntities = new ArrayList<>();
//        for (ProcessTask processTask : processTasks) {
//            if (!processTaskMap.containsKey(
//                    processTask.getProcessId() + ":" + processTask.getTaskId())) {
//                maxId++;
//                processTask.setId(maxId);
//                processTask.setCreatedBy(1);
//                processTask.setCreatedAt(new Date());
//                processTask.setUpdatedBy(1);
//                processTask.setUpdatedAt(new Date());
//                processTask.setDeleted((byte) 0);
//                createEntities.add(processTask);
//            }
//        }
//        List<ProcessTask> createdEntities = processTaskRepository.saveAll(createEntities);
//        createdEntities.addAll(existingProcessTasks);
//        return createdEntities;
        return processTaskRepository.saveAll(processTasks);
    }

    @Override
    public List<ProcessTask> updateProcessTasks(List<ProcessTask> processTasks)
            throws CustomException {
//        for (ProcessTask processTask : processTasks) {
//            if (Objects.isNull(processTask.getProcessId())) {
//                throw new BadRequestException("Process Id cannot be null");
//            }
//            if (Objects.isNull(processTask.getTaskId())) {
//                throw new BadRequestException("Task Id cannot be null");
//            }
//        }
//        Map<String, ProcessTask> existingProcessTaskMap = getByFilter(
//                toProcessTaskFilter(processTasks)).stream().collect(
//                Collectors.toMap(each -> (each.getProcessId() + ":" + each.getTaskId()),
//                        Function.identity(), (oldK, newK) -> oldK));
//        List<ProcessTask> updateEntities = new ArrayList<>();
//        for (ProcessTask processTask : processTasks) {
//            if (!existingProcessTaskMap.containsKey(
//                    processTask.getProcessId() + ":" + processTask.getTaskId())) {
//                throw new BadRequestException("No entity with given process Id and task Id");
//            }
//            updateEntities.add(mapperUtils.mapNonNull(processTask, existingProcessTaskMap.get(
//                    processTask.getProcessId() + ":" + processTask.getTaskId())));
//        }
//        return processTaskRepository.saveAll(updateEntities);
        // Ensure updated timestamps for all tasks
        processTasks.forEach(task -> task.setUpdatedAt(new Date()));

        // Save all tasks directly
        return processTaskRepository.saveAll(processTasks);
    }
    @Override
    public void deleteProcessTask(Integer processTaskId) throws CustomException {
        log.info("Attempting to delete process task with ID: {}", processTaskId);

        if (!processTaskRepository.existsById(processTaskId)) {
            log.error("ProcessTask not found with ID: {}", processTaskId);
            throw new CustomException(ErrorCode.NO_USER_ID_FOUND, "ProcessTask with id " + processTaskId + " doesn't exist");
        }

        processTaskRepository.deleteById(processTaskId);
        log.info("Successfully deleted process task with ID: {}", processTaskId);
    }
    private void validateProcessTask(ProcessTask processTask) throws BadRequestException {
        if (Objects.isNull(processTask.getProcessId())) {
            throw new BadRequestException("Process Id cannot be null");
        }
        if (Objects.isNull(processTask.getTaskId())) {
            throw new BadRequestException("Task Id cannot be null");
        }
        if (Objects.isNull(processTask.getName())) {
            throw new BadRequestException("Name cannot be null");
        }
        if (Objects.isNull(processTask.getOrder())) {
            throw new BadRequestException("Order by cannot be null");
        }
        if (Objects.isNull(processTask.getDescription())) {
            throw new BadRequestException("Description cannot be null");
        }
    }
}
