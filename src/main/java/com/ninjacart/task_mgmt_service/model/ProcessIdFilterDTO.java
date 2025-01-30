package com.ninjacart.task_mgmt_service.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProcessIdFilterDTO {
    private List<Integer> processIds = null;
    private List<String> processInstanceStatus = null;
    private List<String> references = null;
    private List<Integer> assignedToIds = null;
}
