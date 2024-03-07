package com.n11.userservice.errormessage;

import com.n11.userservice.general.BaseErrorMessage;

public enum UserErrorMessage implements BaseErrorMessage {
    USER_IS_NOT_ACTIVE("User is not active!"),
    USER_ALREADY_EXISTS("User already exists!"),
    NO_ACTIVE_USERS("No active users"),
    USER_IS_ALREADY_ACTIVE("User is already active!"),
    USER_IS_ALREADY_PASSIVE("User is already passive!");

    private final String message;

    UserErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
