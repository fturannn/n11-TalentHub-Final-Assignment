package com.n11.userservice.faker;

import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumScore;
import com.n11.userservice.request.UserReviewSaveRequest;

public class UserReviewSaveRequestFaker {

    public UserReviewSaveRequest userReviewSaveRequest() {
        return new UserReviewSaveRequest(1L, 1L,
                "Test Review Text", EnumScore.FOUR);
    }
}
