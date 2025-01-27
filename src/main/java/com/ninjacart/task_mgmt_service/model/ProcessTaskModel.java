package com.ninjacart.task_mgmt_service.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessTaskModel {

    private int id;

    private int processId;

    private String name;

    private String description;

    private Integer minutesToComplete;

    private Integer minutesToAssign;

    private int orderBy;

    private String requestParam;

    private int taskId;

    private Date createdAt;

    private int createdBy;

    private int updatedBy;

    private byte deleted;

    private Boolean forfeitTicketEscalate;

}
