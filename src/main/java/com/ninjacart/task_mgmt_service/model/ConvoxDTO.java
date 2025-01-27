package com.ninjacart.task_mgmt_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvoxDTO {
    private boolean dataUploadFlag;
    private String contactNumber;
    private String priority;
    private Integer pitId;
    private String languageId;
}
