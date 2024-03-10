package com.n11.userservice.faker;

import com.n11.userservice.enums.EnumScore;
import com.n11.userservice.request.UserReviewUpdateRequest;

public class UserReviewUpdateRequestFaker {

    public UserReviewUpdateRequest userReviewUpdateRequest() {
        return new UserReviewUpdateRequest(1L,
                "Test Review Text", EnumScore.FOUR);
    }
}
