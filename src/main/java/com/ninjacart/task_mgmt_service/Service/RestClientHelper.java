package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RestClientHelper {

    private static final String OAUTH_HEADER_AUTH_KEY = "Authorization";
    private static final String HEADER_KEY_BASIC = "Basic ";
    private static final String HEADER_KEY_BEARER = "Bearer ";

    private final HttpHeaders httpHeaders;

    public RestClientHelper() {
        this.httpHeaders = new HttpHeaders();
    }

    public RestClientHelper withAccept(String mediaType) {
        httpHeaders.add(HttpHeaders.ACCEPT, mediaType);
        return this;
    }

    public RestClientHelper withContentType(String mediaType) {
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, mediaType);
        return this;
    }

    public RestClientHelper withAuthorization(String authorization) {
        httpHeaders.add(OAUTH_HEADER_AUTH_KEY, authorization);
        return this;
    }

    public RestClientHelper withAuthorization(HttpServletRequest httpServletRequest) {
        httpHeaders.add(OAUTH_HEADER_AUTH_KEY, httpServletRequest.getHeader(OAUTH_HEADER_AUTH_KEY));
        return this;
    }

    public RestClientHelper withAuth(HttpServletRequest httpServletRequest) {
        String authType = httpServletRequest.getHeader("Auth-Type");
        if ("token".equalsIgnoreCase(authType)) {
            httpHeaders.add("Auth-Type", "token");
        }
        httpHeaders.add(OAUTH_HEADER_AUTH_KEY, httpServletRequest.getHeader(OAUTH_HEADER_AUTH_KEY));
        return this;
    }

    public RestClientHelper withAuth(User user) {
        return withBasicAuth(user.getName(), user.getCredentials().toString());
    }

    public RestClientHelper withBasicAuth(String userName, String password) {
        String auth = userName + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
        httpHeaders.add(HttpHeaders.AUTHORIZATION, HEADER_KEY_BASIC + encodedAuth);
        return this;
    }

    public HttpHeaders build() {
        return this.httpHeaders;
    }
}
