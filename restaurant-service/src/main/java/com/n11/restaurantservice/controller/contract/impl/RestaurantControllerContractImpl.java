package com.n11.restaurantservice.controller.contract.impl;

import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.errormessage.RestaurantErrorMessage;
import com.n11.restaurantservice.general.AppBusinessException;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import com.n11.restaurantservice.service.RestaurantEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantControllerContractImpl implements RestaurantControllerContract {

    private final RestaurantEntityService restaurantEntityService;
    @Override
    public RestaurantDTO getById(Long id) {
        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);

        return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
    }

    @Override
    public List<RestaurantDTO> getAll() {
        List<Restaurant> restaurants = restaurantEntityService.findAll();

        if(restaurants.isEmpty()) {
            throw new AppBusinessException(RestaurantErrorMessage.NO_RESTAURANTS_FOUND);
        }

        return RestaurantMapper.INSTANCE.convertToRestaurantDTOs(restaurants);
    }

    @Override
    public RestaurantDTO save(RestaurantSaveRequest request) {
        Restaurant restaurant = RestaurantMapper.INSTANCE.convertToRestaurant(request);

        Optional<Restaurant> isRestaurantNameExist = restaurantEntityService.getRestaurantByName(request.name());
        Optional<Restaurant> isRestaurantExist = restaurantEntityService.getRestaurantByEmail(request.email());

        if(isRestaurantNameExist.isPresent()) {
            throw new AppBusinessException(RestaurantErrorMessage.DUPLICATE_RESTAURANT_NAME);
        } else if(isRestaurantExist.isPresent()) {
            throw new AppBusinessException(RestaurantErrorMessage.RESTAURANT_ALREADY_EXISTS);
        }

        restaurant = restaurantEntityService.save(restaurant);

        return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
    }

    @Override
    public RestaurantDTO update(Long id, RestaurantUpdateRequest request) {
        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);

        RestaurantMapper.INSTANCE.updateRestaurantFields(restaurant, request);

        restaurant = restaurantEntityService.save(restaurant);

        return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
    }

    @Override
    public void delete(Long id) {
        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);

        restaurantEntityService.delete(restaurant);
    }

    @Override
    public RestaurantDTO updateRestaurantScore(Long id, int newScore) {
        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);

        double total = restaurant.getAverageRating() * restaurant.getTotalReviewNumber() + newScore;
        long newTotalReviewNumber = restaurant.getTotalReviewNumber() + 1;

        restaurant.setTotalReviewNumber(newTotalReviewNumber);
        restaurant.setAverageRating(total / newTotalReviewNumber);

        restaurant = restaurantEntityService.save(restaurant);

        return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
    }
}
