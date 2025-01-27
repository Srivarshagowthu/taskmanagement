package com.ninjacart.task_mgmt_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamProcessTaskDTO {
    private Integer id;
    private Integer processTaskId;
    private String ticketType;
    private Integer ticketTypeId;
    private String priority;
    private String refType;
    private Integer refId;
    private Integer teamId;
    private String cacheKey;
    private String teamName;


}
