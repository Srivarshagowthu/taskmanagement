package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.Exception.GenericException;
import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceTaskCommentsService;
import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceTaskRepository;
import com.ninjacart.task_mgmt_service.Utils.DateUtils;
import com.ninjacart.task_mgmt_service.entity.ProcessInstanceTask;
import com.ninjacart.task_mgmt_service.entity.ProcessTask;
import com.ninjacart.task_mgmt_service.entity.TeamProcessTask;
import com.ninjacart.task_mgmt_service.entity.WorkFlowPayLoad;
import com.ninjacart.task_mgmt_service.model.enums.CommonEnum;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceTaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.ninjacart.task_mgmt_service.Exception.ErrorCode.UNKNOWN_ERROR;

@Service
@Slf4j
public class ProcessInstanceTaskServiceImpl implements ProcessInstanceTaskService {
    @Autowired
    private ProcessInstanceTaskRepository processInstanceTaskRepository;
    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private TeamProcessTaskService teamProcessTaskService;
    @Autowired
    private ProcessInstanceTaskCommentsService processInstanceTaskCommentsService;
    @Override
    public List<ProcessInstanceTask> getProcessInstanceTasksByProcessInstanceIds(List<Integer> processInstanceIds) {
        return processInstanceTaskRepository.findByProcessInstanceIdInAndDeleted(processInstanceIds, (byte)0);
    }
    @Override
    public ProcessInstanceTask createProcessInstanceTaskForProcessInstance(int userId,
                                                                           WorkFlowPayLoad workFlowPayLoad,
                                                                           Integer processInstanceId,
                                                                           int processId, int orderBy,
                                                                           int assignedTo, Date createdAt) throws CyborgException {
        String stakeHolderName = workFlowPayLoad.getActionObject().get("name") != null ? workFlowPayLoad.getActionObject().get("name").toString() : null;
        String stakeHolderId = workFlowPayLoad.getActionObject().get("id") != null ? workFlowPayLoad.getActionObject().get("id").toString() : null;
        if(workFlowPayLoad.getPriority() == null) {
            workFlowPayLoad.setPriority(5);
        }
        //check the actionObject if we are passing the refType and refId to fetch the actual ticketTypeId --> refType = LOAN_PRODUCT is only for processType = 134
        if(Objects.nonNull(workFlowPayLoad.getActionObject()) && workFlowPayLoad.getActionObject().containsKey("refType")
                && workFlowPayLoad.getActionObject().containsKey("refId")
                && workFlowPayLoad.getActionObject().containsKey("ticketType")
                && workFlowPayLoad.getActionObject().get("refType").toString().equalsIgnoreCase("LOAN_PRODUCT")){
            ProcessTask processTask = processTaskService.findProcessTaskByProcessIdAndOrder(processId, orderBy);
            int ticketTypeRefId = 0;
            try{
                ticketTypeRefId = Integer.parseInt(workFlowPayLoad.getActionObject().get("refId").toString());
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
            }

            List<TeamProcessTask> teamProcessTaskList =  teamProcessTaskService.getTeamProcessTaskForRef(processTask.getId(),workFlowPayLoad.getActionObject().get("ticketType").toString(), ticketTypeRefId,workFlowPayLoad.getActionObject().get("refType").toString() );
            //get the actual type from teamProcessTask table if it is exists, else for old cases it will be from teamProcessTaskEnum
            if(!teamProcessTaskList.isEmpty()){
                workFlowPayLoad.setType(teamProcessTaskList.get(0).getTicketTypeId());
            }
        }


        ProcessInstanceTask processInstanceTask = createProcessInstanceTask(userId,
                assignedTo,
                processInstanceId,
                processId,
                orderBy,
                workFlowPayLoad.getPriority(),
                stakeHolderName,
                stakeHolderId,
                createdAt,
                workFlowPayLoad.getType(),
                workFlowPayLoad.getNextAssignedTo(),
                workFlowPayLoad.getPostponedDate(),
                workFlowPayLoad.getPiStatus());

        processInstanceTaskCommentsService.createProcessInstanceTaskCommentForPIT(userId, processInstanceTask.getId(), workFlowPayLoad);

        return processInstanceTask;
    }
    @Override
    public ProcessInstanceTask createProcessInstanceTask(int userId,
                                                         int assignedTo,
                                                         int processInstanceId,
                                                         int processId,
                                                         int orderBy,
                                                         Integer paramPriority,
                                                         String stakeholderName,
                                                         String stakeholderId,
                                                         Date creationDate,
                                                         Integer type,
                                                         Integer nextAssignedTo,
                                                         Date postponedDate,
                                                         ProcessInstanceStatus piStatus) throws CyborgException {
        ProcessTask processTask = processTaskService.findProcessTaskByProcessIdAndOrder(processId, orderBy);
        Boolean isHighestPriority = false;
        if(processId == 74 && paramPriority == 25) {         // Central Investigation (Case for NO_RESPONSE_REGENERATE_TICKET)
            isHighestPriority = true;
        }
//        int priority = ProcessTaskPriorityHelper.getTaskPriority(processTask.getDefaultPriority(), paramPriority, isHighestPriority);
        int priority = 5;
        if (Objects.nonNull(paramPriority)) {
            priority = paramPriority;
        }
        ProcessInstanceTask processInstanceTask = new ProcessInstanceTask();
        if(processId == 171) {
            List<ProcessInstanceTask> processInstanceTaskList = processInstanceTaskRepository.findByProcessInstanceIdInAndDeleted(Collections.singletonList(processInstanceId), (byte)0);
            if(!CollectionUtils.isEmpty(processInstanceTaskList)) {
                ProcessInstanceTask existingProcessInstanceTask = processInstanceTaskList.get(0);
                processInstanceTask.setId(existingProcessInstanceTask.getId());
                processInstanceTask.setCreatedAt(existingProcessInstanceTask.getCreatedAt());
                assignedTo = existingProcessInstanceTask.getAssignedTo();
                creationDate = existingProcessInstanceTask.getCreatedAt();
            }
        }
        processInstanceTask.setProcessInstanceId(processInstanceId);
        processInstanceTask.setAssignedTo(assignedTo);
        if(userId != CommonEnum.SYSTEM_USER) {
            processInstanceTask.setAssignedAt(new Date());
        }
        String newName = processTask.getName();
        if (stakeholderName != null && !stakeholderName.isEmpty()) {
            newName = newName.replace("<name>", stakeholderName);
        }
        if (stakeholderId != null) {
            newName = newName.replace("<id>", stakeholderId);
        }

        processInstanceTask.setName(newName);
//        processInstanceTask.setName(CommonEnum.SYSTEM_GENERATED_PROCESS_TASK_NAME);
        processInstanceTask.setDescription(CommonEnum.SYSTEM_GENERATED_PROCESS_TASK_DESCRIPTION + ""
                + " " + processTask.getName() + " ProcessEnum ");
        processTask.setId(processTask.getId());
        processInstanceTask.setProcessTaskId(processTask.getId());

        processInstanceTask.setStatus(ProcessInstanceTaskStatus.PENDING);
        if (Objects.nonNull(piStatus)) {
            ProcessInstanceTaskStatus pitStatus = ProcessInstanceTaskStatus
                    .getProcessInstanceTaskStatusByText(piStatus.name());
            processInstanceTask.setStatus(pitStatus);
        }

        // Task Date Stored in IST
        Date dueDate = null;
        if(postponedDate != null){
            processInstanceTask.setStatus(ProcessInstanceTaskStatus.POSTPONED);
            setDueDate(processInstanceTask, processTask, postponedDate);
            setTaskDate(processInstanceTask, postponedDate);
        }
        else{
            setDueDate(processInstanceTask, processTask, new Date());
            setTaskDate(processInstanceTask, creationDate);
        }
        processInstanceTask.setPriority(priority);
        processInstanceTask.setNextAssignedTo(nextAssignedTo);
        processInstanceTask.setPostponedDate(postponedDate);
        if(Objects.nonNull(type)){

            processInstanceTask.setType(type);
        }
        processInstanceTaskRepository.saveAndFlush(processInstanceTask);
        return processInstanceTask;
    }
    void setDueDate(ProcessInstanceTask processInstanceTask, ProcessTask processTask, Date baseDate){
        Date dueDate = DateUtils.addMinutesToDate(baseDate, CommonEnum.ONE_WEEK_IN_MINUTES);

        if (processTask.getMinutesToComplete() != null) {
            dueDate = DateUtils.addMinutesToDate( baseDate, processTask.getMinutesToComplete());
        }
        processInstanceTask.setDueDate(dueDate);
    }


    void setTaskDate(ProcessInstanceTask processInstanceTask, Date baseDate) throws GenericException {
        Date taskDate=null;
        try{
            taskDate = DateUtils.getLocalDate();
            if(baseDate != null) {
                taskDate = DateUtils.getDate(DateUtils.dateFormatSort(baseDate));
            }
        }catch (Exception e){
            throw new GenericException(UNKNOWN_ERROR.name(),UNKNOWN_ERROR.getErrorMessage());
        }

        processInstanceTask.setTaskDate(taskDate);
    }


}
