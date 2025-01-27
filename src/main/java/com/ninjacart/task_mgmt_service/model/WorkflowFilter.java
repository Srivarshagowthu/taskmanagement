package com.ninjacart.task_mgmt_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowFilter {
    private int processId;
    private List<String> references;
}
