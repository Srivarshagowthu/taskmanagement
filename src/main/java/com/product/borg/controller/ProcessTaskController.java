package com.product.borg.controller;

import com.product.borg.helper.exception.CustomException;
import com.product.borg.helper.exception.errorcode.ErrorCode;
import com.product.borg.model.ApiResponse;
import com.product.borg.model.ProcessTaskFilter;
import com.product.borg.persistance.dao.ProcessTaskRepository;
import com.product.borg.persistance.entity.ProcessTask;
import com.product.borg.service.ProcessTaskService;
import com.product.borg.utils.ResponseUtil;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/process-tasks")//. /processTask
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
public class ProcessTaskController {

    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private ProcessTaskRepository processTaskRepository;
    @GetMapping
    public ApiResponse getAllProcessTasks() {
        log.info("Received request to fetch all process tasks");

        List<ProcessTask> processTasks = processTaskService.getAllProcessTasks();

        log.info("Successfully fetched {} process tasks", processTasks.size());

        return ResponseUtil.jsonResponse(processTasks);
    }
//    @PostMapping("/filters")
//    public ApiResponse getByFilter(@RequestBody ProcessTaskFilter processTaskFilter) {
//        log.info("Received request to filter process tasks: {}", processTaskFilter);
//
//        List<ProcessTask> responseData = processTaskService.getByFilter(processTaskFilter);
//
//        log.info("Response data before wrapping in ApiResponse: {}", responseData);
//
//        ApiResponse response = ResponseUtil.jsonResponse(responseData);
//
//        log.info("Returning response: {}", response);
//
//        return response;
//    }
    @PostMapping
    public ApiResponse createProcessTasks(@RequestBody List<ProcessTask> processTasks) throws CustomException {
        log.info("Received request to create tasks: {}", processTasks);
        List<ProcessTask> createProcessTasks = processTaskService.createProcessTasks(processTasks);
        log.info("Successfully created tasks: {}", processTasks);
        return ResponseUtil.jsonResponse(createProcessTasks);
    }
    @PutMapping
    public ApiResponse updateProcessTasks(@RequestBody List<ProcessTask> processTasks) throws CustomException {
        log.info("Received request to update tasks : {}", processTasks);
        List<ProcessTask> updateProcessTasks = processTaskService.updateProcessTasks(processTasks);
        log.info("Successfully updated tasks: {}", processTasks);
        return ResponseUtil.jsonResponse(updateProcessTasks);
    }
    @DeleteMapping("/{id}")
    public ApiResponse deleteProcessTaskById(@PathVariable Integer id) throws CustomException {
        log.info("Received request to delete task with ID: {}", id);
        processTaskService.deleteProcessTask(id);
        log.info("Successfully deleted task with ID: {}", id);
        return ResponseUtil.jsonResponse("Task deleted successfully");
    }

}