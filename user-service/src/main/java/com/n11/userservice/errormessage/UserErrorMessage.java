package com.n11.userservice.errormessage;

import com.n11.userservice.general.BaseErrorMessage;

public enum UserErrorMessage implements BaseErrorMessage {
    USER_IS_NOT_ACTIVE("User is not active!"),
    USER_ALREADY_EXISTS("User already exists!")
    ;

    private final String message;

    UserErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String toString() {
        return message;
    }
}
