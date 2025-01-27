package com.ninjacart.task_mgmt_service.model;

import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class ProcessURLConfiguration {
    public String getURL(int processId) {
        DynamicStringProperty dynamicStringProperty = DynamicPropertyFactory.getInstance().getStringProperty(String.valueOf(processId),new String());
        log.info(" dynamicStringProperty ===> " + dynamicStringProperty);
        if(dynamicStringProperty == null) {
            return null;
        }
        return dynamicStringProperty.getValue();
    }

    public String getProperty(String property, String def) {
        DynamicStringProperty dynamicStringProperty = DynamicPropertyFactory.getInstance().getStringProperty(property, def);
        return dynamicStringProperty.get();
    }

    public Boolean getBooleanProperty(String property, Boolean def) {
        DynamicBooleanProperty dynamicBooleanProperty = DynamicPropertyFactory.getInstance().getBooleanProperty(property, def);
        return dynamicBooleanProperty.get();
    }

    public List<Integer> getIntegerProperty(String property, String def) {
        DynamicStringProperty dynamicStringProperty = DynamicPropertyFactory.getInstance().getStringProperty(property, def);
        String value = dynamicStringProperty.get();
        return CommonUtils.commaSeparatedStringToList(value);
    }

    public Integer getIntegerProperty(String property, Integer def) {
        DynamicIntProperty dynamicBooleanProperty = DynamicPropertyFactory.getInstance().getIntProperty(property, def);
        return dynamicBooleanProperty.get();
    }

}

