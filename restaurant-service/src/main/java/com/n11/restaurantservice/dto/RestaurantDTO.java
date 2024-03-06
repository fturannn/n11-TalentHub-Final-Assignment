package com.n11.restaurantservice.dto;

import com.n11.restaurantservice.enums.EnumCuisineType;
import com.n11.restaurantservice.enums.EnumFeatureType;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record RestaurantDTO(Long id,
                            String name,
                            String phoneNumber,
                            String email,
                            String country,
                            String city,
                            String district,
                            LocalTime openingHour,
                            LocalTime closingHour,
                            EnumCuisineType cuisineType,
                            List<EnumFeatureType> features,
                            double averageRating,
                            String description) {
}