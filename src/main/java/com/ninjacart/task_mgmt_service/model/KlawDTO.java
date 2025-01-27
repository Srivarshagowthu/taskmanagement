package com.ninjacart.task_mgmt_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KlawDTO {
    private String id;
    private String referenceId;
    private String callerNumber;
    private String receiverNumber;
    private String callType;
    private String startTime;
    private String endTime;
    private int duration;
    private String callerStatus;
    private String receiverStatus;
    private String recordingUrl;
    private String networkProviderLocation;
    private String networkProvider;
    private int keyPress;
    private String serviceProvider;
    private String serviceProviderRefId;
}
