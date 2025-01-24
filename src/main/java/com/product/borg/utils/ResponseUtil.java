package com.product.borg.utils;

import com.product.borg.model.ApiResponse;
import jakarta.ws.rs.core.Response;


public class ResponseUtil {

    public static ApiResponse jsonResponse(String msg, Response.Status status) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(status.getStatusCode());
        apiResponse.setMessage(msg);
        return apiResponse;
    }

    public static ApiResponse jsonResponse(Object data) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        apiResponse.setData(data);
        return apiResponse;
    }
}
