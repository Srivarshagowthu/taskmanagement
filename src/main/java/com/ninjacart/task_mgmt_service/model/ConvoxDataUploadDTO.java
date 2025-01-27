package com.ninjacart.task_mgmt_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
//Note: Do not change the variable names
public class ConvoxDataUploadDTO {
    private String action = "DATAUPLOAD";
    private String refno = "123456789";
    private String process = "CRM";
    private List<ConvoxData> data;
}
