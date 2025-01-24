package com.product.borg.helper.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    CLIENT_TIMEOUT("Request to client timed out"),
    SERVER_UNREACHABLE("Destination server unreachable"), // Corrected typo
    REQUEST_TIMEOUT("Request to destination timed out"), // More consistent naming
    CLIENT_ERROR("Client error during request"),
    EMPTY_RESPONSE("Empty response from client"),
    NO_USER_ID_FOUND("User ID not found in the query"), // More natural phrasing
    UNKNOWN_ERROR("An unexpected error occurred"), // More natural phrasing
    GENERIC_ERROR("%s"); // More descriptive name

    private final String errorMessage; // Make errorMessage final
}