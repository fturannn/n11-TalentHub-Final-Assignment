package com.n11.restaurantservice.request;

import com.n11.restaurantservice.enums.EnumCuisineType;
import com.n11.restaurantservice.enums.EnumFeatureType;

import java.time.LocalTime;
import java.util.List;

public record RestaurantUpdateRequest(Long id,
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
