package com.ninjacart.task_mgmt_service.Repository;

//import com.ninjacart.task_mgmt_service.entity.ProcessInstance;
import com.ninjacart.task_mgmt_service.entity.ProcessInstance;
import com.ninjacart.task_mgmt_service.model.ProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.model.StakeholderProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;
import org.springframework.stereotype.Repository;


import java.util.List;

public interface ProcessInstanceRepositoryCustom {
    //List<ProcessInstance> getProcessInstances(List<Integer> ids);

    List<ProcessInstance> getProcessInstances(List<Integer> processIds, List<ProcessInstanceStatus> statusList, List<String> references, Integer limit, Integer offset, String fromDate, String toDate, List<String> referenceTypeList);

    //ProcessInstanceStatus> statusList, List<String> references, Integer limit, Integer offset, String fromDate, String toDate, List<String> referenceTypeList);
    List<StakeholderProcessInstanceDTO> getActiveProcessForStakeholder(int stakeholderId, String domainName, List<Integer> ignoreProcessIds, Integer thresholdMinutes);

    List<ProcessInstanceDTO> findOpenPIByReferenceAndExternalReferenceForOndc(String reference, String externalReference);

    List<ProcessInstance> fetchOpenPIsByReferenceAndProcess(String reference, Integer processId);
}

