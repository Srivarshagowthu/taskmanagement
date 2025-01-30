package com.ninjacart.task_mgmt_service.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessVariableMapFilter {

    private List<Integer> processIds;
    private List<Integer> variableIds;
}
