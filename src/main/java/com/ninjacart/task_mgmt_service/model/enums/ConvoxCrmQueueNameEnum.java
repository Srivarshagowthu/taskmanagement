package com.ninjacart.task_mgmt_service.model.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ConvoxCrmQueueNameEnum {
    ENGLISH("1", "English_CRM"),
    HINDI("2", "HINDI_CRM"),
    KANNADA("3", "KANNADA_CRM"),
    TAMIL("5", "TAMIL_CRM"),
    TELUGU("6", "TELUGU_CRM")
    ;

    private String languageId;
    private String queueName;

    public static ConvoxCrmQueueNameEnum findByLanguageId(String languageId) {
        return Arrays.stream(ConvoxCrmQueueNameEnum.values()).filter(each -> each.getLanguageId().equals(languageId))
                .findFirst().orElse(ConvoxCrmQueueNameEnum.KANNADA);
    }
}
