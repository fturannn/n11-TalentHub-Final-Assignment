package com.n11.restaurantservice.dto;

import com.n11.restaurantservice.entity.Feature;
import com.n11.restaurantservice.enums.EnumCuisineType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalTime;
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
                            Set<Feature> features,
                            double averageRating,
                            String description) {
}
