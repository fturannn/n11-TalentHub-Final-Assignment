package com.n11.restaurantservice.dto;

import com.n11.restaurantservice.enums.EnumFeatureType;

public record FeatureDTO(Long id,
                         EnumFeatureType featureType) {
}
