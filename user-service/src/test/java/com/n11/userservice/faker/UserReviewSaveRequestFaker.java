package com.n11.userservice.faker;

import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumScore;
import com.n11.userservice.request.UserReviewSaveRequest;

public class UserReviewSaveRequestFaker {

    public UserReviewSaveRequest userReviewSaveRequest() {
        return new UserReviewSaveRequest(1L, "59bd8e58-7cd9-44b6-9a6e-040aca58f639",
                "Test Review Text", EnumScore.FOUR);
    }
}
