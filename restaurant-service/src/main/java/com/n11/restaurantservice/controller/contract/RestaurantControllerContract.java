package com.n11.restaurantservice.controller.contract;

import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;

import java.util.List;

public interface RestaurantControllerContract {

    RestaurantDTO getById(String id);
    List<RestaurantDTO> getAll();
    RestaurantDTO save(RestaurantSaveRequest request);
    RestaurantDTO update(String id, RestaurantUpdateRequest request);
    void delete(String id);
    RestaurantDTO updateRestaurantScore(String id, int newScore);
}
