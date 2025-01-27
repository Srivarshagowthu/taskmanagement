package com.ninjacart.task_mgmt_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationInfoDTO {
    private String reference;
    private Integer processId;
    private String cityName;
    private Integer cityId;
}
