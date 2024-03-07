package com.n11.restaurantservice.controller.contract;

import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;

import java.util.List;

public interface RestaurantControllerContract {

    RestaurantDTO getById(Long id);
    List<RestaurantDTO> getAll();
    RestaurantDTO save(RestaurantSaveRequest request);
    RestaurantDTO update(Long id, RestaurantUpdateRequest request);
    void delete(Long id);
    RestaurantDTO updateRestaurantScore(Long id, int newScore);
}
