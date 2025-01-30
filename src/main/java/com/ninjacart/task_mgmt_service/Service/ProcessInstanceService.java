package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.entity.ProcessInstance;
import com.ninjacart.task_mgmt_service.model.AutoComplete;
import com.ninjacart.task_mgmt_service.model.ProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.entity.WorkFlowPayLoad;
import com.ninjacart.task_mgmt_service.model.StakeholderProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.model.User;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ProcessInstanceService {
    List<StakeholderProcessInstanceDTO> getActiveProcessForStakeholder(int stakeholderId, String domainName, List<Integer> ignoreProcessIds, Integer thresholdMinutes);
    //List<StakeholderProcessInstanceDTO> getActiveProcessForStakeholder(int stakeholderId, String domainName, List<Integer> ignoreProcessIds, Integer thresholdMinutes);

    void updateProcessInstances(List<ProcessInstance> processInstances);

    List<ProcessInstanceDTO> createProcess(User principal, HttpServletRequest request, List<WorkFlowPayLoad> workFlowPayLoads) throws Exception;

    List<ProcessInstance> findOpenPIsByReferenceAndProcess(String reference, Integer processId) throws Exception;

    List<ProcessInstanceDTO> findOpenPIByReferenceAndExternalReferenceForOndc(String reference, String externalReference) throws Exception;
    //void autoComplete(User principal, AutoComplete autoComplete) throws CyborgException;

    //void autoComplete(List<ProcessInstance> processInstances);
    List<ProcessInstance> getProcessInstances(List<Integer> processIds, List<ProcessInstanceStatus> statusList, List<String> references, Integer limit, Integer offset, String fromDate, String toDate);

}
