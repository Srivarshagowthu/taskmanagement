package com.ninjacart.task_mgmt_service.controller;

import com.ninjacart.task_mgmt_service.Service.ProcessInstanceService;
import com.ninjacart.task_mgmt_service.Service.ProcessInstanceServiceHelper;
import com.ninjacart.task_mgmt_service.model.ProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.entity.WorkFlowPayLoad;
import com.ninjacart.task_mgmt_service.model.ApiResponse;
import com.ninjacart.task_mgmt_service.model.ResponseUtil;
import com.ninjacart.task_mgmt_service.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/processInstances")
public class ProcessInstanceController {

    @Autowired
    private ProcessInstanceService processInstanceService;

    @Autowired
    private ProcessInstanceServiceHelper processInstanceServiceHelper;

    @PostMapping
    public ApiResponse createProcess(User principal, HttpServletRequest request,
                                     @RequestBody List<WorkFlowPayLoad> workFlowPayLoads) throws Exception {

        List<ProcessInstanceDTO> processInstanceDTOList = processInstanceService.createProcess(principal,request,workFlowPayLoads);

        return ResponseUtil.jsonResponse(processInstanceDTOList);
    }
}