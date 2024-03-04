package com.n11.userservice.dto;

import com.n11.userservice.enums.EnumScore;

import java.time.LocalDateTime;

public record UserReviewDTO(Long id,
                            String userName,
                            String userSurname,
                            String userFullName,
                            String restaurantId,
                            String restaurantName,
                            String text,
                            LocalDateTime reviewDate,
                            EnumScore score) {
}
