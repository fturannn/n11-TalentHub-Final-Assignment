package com.n11.userservice.dto;

import com.n11.userservice.enums.EnumCuisineType;
import com.n11.userservice.enums.EnumFeatureType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class RestaurantDTO {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String country;
    private String city;
    private String district;
    private double latitude;
    private double longitude;
    private LocalTime openingHour;
    private LocalTime closingHour;
    private EnumCuisineType cuisineType;
    private List<EnumFeatureType> features;
    private Long totalReviewNumber;
    private double averageRating;
    private String description;
}
