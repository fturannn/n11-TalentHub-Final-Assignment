package com.n11.userservice.errormessage;

import com.n11.userservice.general.BaseErrorMessage;

public enum UserReviewErrorMessage implements BaseErrorMessage {
    REVIEW_NOT_FOUND("Review not found!"),
    NO_REVIEWS_AVAILABLE("No reviews available!"),
    ;

    private final String message;

    UserReviewErrorMessage(String message) {
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
