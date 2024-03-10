package com.n11.userservice.request;

import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumScore;

import java.time.LocalDateTime;

public record UserReviewSaveRequest(Long userId,
                                    Long restaurantId,
                                    String text,
                                    EnumScore score) {
}
