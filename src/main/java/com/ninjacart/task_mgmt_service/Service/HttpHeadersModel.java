package com.ninjacart.task_mgmt_service.Service;

import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpHeadersModel {
    private String authorization;
}