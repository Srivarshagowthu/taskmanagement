package com.ninjacart.task_mgmt_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldVerificationCsvDataDto {
    private Integer processId;
    private Integer ticketId;
    private String status;
    private Integer assignedTo;
    private String classification;
    private String callFeedback;
    private String startTime;
    private String duration;
    private String audioUrls;
    private String shopImages;

    Integer customerId;
    Integer fvAuditId;
    String reason;
    //    String status;
//    Integer ticketId;
    String changeType;
    String oldValue;
    String newValue;

    //    Integer agentId;
    String ticketStatus;
    String taskDate;
    String videoUrl;

}
