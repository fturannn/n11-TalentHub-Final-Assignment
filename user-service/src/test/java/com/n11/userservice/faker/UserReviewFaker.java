package com.n11.userservice.faker;

import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.enums.EnumScore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserReviewFaker {
    public List<UserReview> userReviewList() {
        UserReview userReview1 = new UserReview();
        userReview1.setId(1L);
        userReview1.setUser(new User());
        userReview1.setRestaurantId(1L);
        userReview1.setText("Test review 1");
        userReview1.setReviewDate(LocalDateTime.now());
        userReview1.setScore(EnumScore.FOUR);

        UserReview userReview2 = new UserReview();
        userReview2.setId(2L);
        userReview2.setUser(new User());
        userReview2.setRestaurantId(2L);
        userReview2.setText("Test review 2");
        userReview2.setReviewDate(LocalDateTime.now());
        userReview2.setScore(EnumScore.THREE);

        List<UserReview> userReviewList = new ArrayList<>();
        userReviewList.add(userReview1);
        userReviewList.add(userReview2);

        return userReviewList;
    }

    public UserReview userReview() {
        UserReview userReview = new UserReview();
        userReview.setId(1L);
        userReview.setUser(new User());
        userReview.setRestaurantId(1L);
        userReview.setText("Test review 1");
        userReview.setReviewDate(LocalDateTime.now());
        userReview.setScore(EnumScore.FOUR);
        return userReview;
    }
}
