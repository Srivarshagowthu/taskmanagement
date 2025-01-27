package com.ninjacart.task_mgmt_service.model.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Getter
public enum LanguagePreferenceEnum {

    ENGLISH(1, "English", "ENG"),
    HINDI(2, "Hindi", "HIN"),
    KANNADA(3, "Kannada", "KAN"),
    MALAYALAM(4, "Malayalam", "MLM"),
    TAMIL(5, "Tamil", "TAM"),
    TELUGU(6, "Telugu", "TEL"),
    MARATHI(7, "Marathi", "MAR"),
    GUJARATI(8, "Gujarati", "GUJ");

    private int code;
    private String description;
    private String langCode;

    LanguagePreferenceEnum(int code, String description, String langCode) {
        this.code = code;
        this.description = description;
        this.langCode = langCode;
    }

    public static LanguagePreferenceEnum fromDescription(String text) {
        for(LanguagePreferenceEnum lang : LanguagePreferenceEnum.values()) {
            if(lang.description.equalsIgnoreCase(text)) {
                return  lang;
            }
        }
        return null;
    }

    public static LanguagePreferenceEnum fromCode(Integer code) {
        return Arrays.stream(LanguagePreferenceEnum.values())
                .filter(each -> each.getCode() == code).findFirst().orElse(LanguagePreferenceEnum.ENGLISH);
    }
}