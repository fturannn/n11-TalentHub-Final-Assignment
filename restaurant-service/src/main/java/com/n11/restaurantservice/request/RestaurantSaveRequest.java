package com.n11.restaurantservice.request;

import com.n11.restaurantservice.enums.EnumCuisineType;
import com.n11.restaurantservice.enums.EnumFeatureType;

import java.time.LocalTime;
import java.util.List;

public record RestaurantSaveRequest(String name,
                                    String phoneNumber,
                                    String email,
                                    String country,
                                    String city,
                                    String district,
                                    double latitude,
                                    double longitude,
                                    LocalTime openingHour,
                                    LocalTime closingHour,
                                    EnumCuisineType cuisineType,
                                    List<EnumFeatureType> features,
                                    String description) {
}
