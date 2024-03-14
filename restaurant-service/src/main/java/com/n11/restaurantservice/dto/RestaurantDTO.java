package com.n11.restaurantservice.dto;

import java.time.LocalTime;

public record RestaurantDTO(String id,
                            String name,
                            String phoneNumber,
                            String email,
                            String country,
                            String city,
                            String district,
                            Double latitude,
                            Double longitude,
                            LocalTime openingHour,
                            LocalTime closingHour,
                            Long totalReviewNumber,
                            Double averageRating,
                            String description) {
}
