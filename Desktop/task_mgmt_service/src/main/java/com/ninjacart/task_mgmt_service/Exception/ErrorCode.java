package com.ninjacart.task_mgmt_service.Exception;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorCode {
    CLIENT_TIMEOUT("Request to client timed out"),
    SERVER_UNREACHABLE("Destination server unreacheble"),
    TIME_OUT("Destination request timeout"),
    CLIENT_ERROR("Client error during request"),
    EMPTY_RESPONSE("Empty response from client"),
    NO_USER_ID_FOUND("User Id not returned in the query"),
    UNKNOWN_ERROR("Unexpected error occured"),
    ERROR("%s");

    private String errorMessage;

}
