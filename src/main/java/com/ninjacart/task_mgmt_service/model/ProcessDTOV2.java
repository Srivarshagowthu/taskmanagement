package com.ninjacart.task_mgmt_service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDTOV2 {
    private Integer id;
    private String name;
    private Boolean allowUpdate;
}
