package com.product.borg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T>  {
    private boolean success=true;
    private String errorCode;
    private String errorMessage;
    private String code;
    private String message;
    private int status;
    private T data;

    public ApiResponse(T data){
        this.data=data;
    }
}
