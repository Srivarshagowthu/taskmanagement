package com.ninjacart.task_mgmt_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessTaskActionModel {

    private int id;

    private int processTaskId;

    private String action;

    private Integer nextProcessTaskId;

    private Date createdAt;

    private int createdBy;

    private int updatedBy;

    private byte deleted;

}
