package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.model.ProcessURLConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Configuration
public class RestUrl {


    @Autowired
    private ProcessURLConfiguration processURLConfiguration;
    //Convox
    private static final String END_CALL_URL = "/External/external_dialer.php";

    public String getConvoxEndCallUrl(Integer pitId) {
        String baseURL = processURLConfiguration.getProperty("convox.baseUrl", "http://b12.deepijatel.in:8024/ConVoxCCS");
        log.info("ConvoxBaseUrl for ticketId {} --> {}", pitId, baseURL);
        return baseURL + END_CALL_URL;
    }
}