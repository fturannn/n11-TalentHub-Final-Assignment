package com.n11.restaurantservice.mapper;

import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);
    Restaurant convertToRestaurant(RestaurantSaveRequest request);
    RestaurantDTO convertToRestaurantDTO(Restaurant restaurant);
    List<RestaurantDTO> convertToRestaurantDTOs(List<Restaurant> restaurants);
    @Mapping(target = "id", ignore = true)
    void updateRestaurantFields(@MappingTarget Restaurant restaurant, RestaurantUpdateRequest request);
}
