package com.ninjacart.task_mgmt_service.Repository;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.entity.ProcessInstance;
import com.ninjacart.task_mgmt_service.entity.ProcessInstanceTask;
import com.ninjacart.task_mgmt_service.entity.WorkFlowPayLoad;

import com.ninjacart.task_mgmt_service.model.*;
import com.ninjacart.task_mgmt_service.model.enums.CommonEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

public interface ProcessInstanceHandler {

    int getProcessId();

    void updateProcessInstanceTask(User user, HttpServletRequest request,
                                   ProcessInstance processInstance, ProcessInstanceTask processInstanceTask,
                                   UpdateProcessInstanceTaskDTO processInstanceTaskDTO) throws Exception;

    default void processTaskRelatedData(HttpServletRequest request, ProcessInstanceTaskDTO processInstanceTaskDTO) throws CyborgException, IOException {

    }


    default List<TaskInfoDTO> getTaskInfo(Integer processTaskId, String date, List<Integer> assignedToList) throws CyborgException, ParseException {
        return null;
    }
    default Collection<LocationInfoDTO> getLocationInfo(HttpServletRequest httpServletRequest, WorkflowFilter filter) throws CyborgException {
        return Collections.emptyList();
    }
    default void autoCompleteBasedOnTime(HttpServletRequest httpServletRequest, User user) throws CyborgException {

    }

    default void closeTickets(HttpServletRequest httpServletRequest, User user) throws CyborgException {

    }

    default void callAsgardOrderTakingService(HttpHeadersModel httpHeadersModel, User user, List<ProcessInstanceTask> processInstanceTasks) throws CyborgException {

    }

    default void escalateTask(HttpServletRequest request, ProcessInstanceTaskDTO processInstanceTaskDTO) throws CyborgException, IOException{

    }

    default List<WorkFlowPayLoad> preProcessorTicketCreation(HttpServletRequest request, User user, List<WorkFlowPayLoad> workFlowPayLoadList) throws CyborgException, IOException{
        return workFlowPayLoadList;
    }

    default boolean checkTicketCreateValidationRequired(Integer type) {
        return true;
    }

    default ConvoxDTO convoxDataUploadBuild(HttpServletRequest request, WorkFlowPayLoad workFlowPayLoad) throws CyborgException {
        return ConvoxDTO.builder().build();
    }

    default int getAssignedToAgentId(HttpServletRequest request, WorkFlowPayLoad workFlowPayLoad) throws Exception {
        if (Objects.nonNull(workFlowPayLoad.getActionObject())
                && Objects.nonNull(workFlowPayLoad.getActionObject().get("assignedTo"))) {
            return Integer.parseInt(workFlowPayLoad.getActionObject().get("assignedTo").toString());
        }
        return CommonEnum.SYSTEM_USER;
    }
}
