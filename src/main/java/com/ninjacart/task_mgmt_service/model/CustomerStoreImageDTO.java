package com.ninjacart.task_mgmt_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStoreImageDTO {
    public Integer customerId;
    public Integer approvalStatus;
    public String status;
    public String storeImageUrl;
}
