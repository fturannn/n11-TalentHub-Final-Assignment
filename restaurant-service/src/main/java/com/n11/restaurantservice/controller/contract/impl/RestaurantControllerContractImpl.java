package com.n11.restaurantservice.controller.contract.impl;

import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.errormessage.RestaurantErrorMessage;
import com.n11.restaurantservice.general.AppBusinessException;
import com.n11.restaurantservice.general.RabbitProducerService;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import com.n11.restaurantservice.service.RestaurantEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantControllerContractImpl implements RestaurantControllerContract {

    private final RestaurantEntityService restaurantEntityService;
    private final RabbitProducerService rabbitProducerService;
    @Override
    public RestaurantDTO getById(String id) {
        rabbitProducerService.sendInfoMessage("Getting restaurant by ID: " + id);

        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);

        rabbitProducerService.sendInfoMessage("Retrieved restaurant: " + restaurant.getName());

        return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
    }

    @Override
    public List<RestaurantDTO> getAll() {
        rabbitProducerService.sendInfoMessage("Getting all restaurants");

        Iterable<Restaurant> restaurants = restaurantEntityService.findAll();
        List<Restaurant> restaurantList = new ArrayList<>();

        if(!restaurants.iterator().hasNext()) {
            throw new AppBusinessException(RestaurantErrorMessage.NO_RESTAURANTS_FOUND);
        } else {
            restaurants.forEach(restaurantList::add);
        }

        rabbitProducerService.sendInfoMessage("Retrieved " + restaurantList.size() + " restaurants");

        return RestaurantMapper.INSTANCE.convertToRestaurantDTOs(restaurantList);
    }

    @Override
    public RestaurantDTO save(RestaurantSaveRequest request) {
        rabbitProducerService.sendInfoMessage("Saving new restaurant: " + request.name());

        Restaurant restaurant = RestaurantMapper.INSTANCE.convertToRestaurant(request);

        Optional<Restaurant> isRestaurantNameExist = restaurantEntityService.getRestaurantByName(request.name());

        if(isRestaurantNameExist.isPresent()) {
            throw new AppBusinessException(RestaurantErrorMessage.DUPLICATE_RESTAURANT_NAME);
        }

        restaurant.setTotalReviewNumber(0L);
        restaurant.setAverageRating(0.0);

        restaurant = restaurantEntityService.save(restaurant);

        rabbitProducerService.sendInfoMessage("Saved new restaurant: " + request.name());

        return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
    }

    @Override
    public RestaurantDTO update(String id, RestaurantUpdateRequest request) {
        rabbitProducerService.sendInfoMessage("Updating restaurant with ID: " + request.id());

        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);

        RestaurantMapper.INSTANCE.updateRestaurantFields(restaurant, request);

        restaurant = restaurantEntityService.save(restaurant);

        rabbitProducerService.sendInfoMessage("Updated restaurant with ID: " + request.id());

        return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
    }

    @Override
    public void delete(String id) {
        rabbitProducerService.sendInfoMessage("Deleting restaurant with ID: " + id);

        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);

        restaurantEntityService.delete(restaurant);

        rabbitProducerService.sendInfoMessage("Deleted restaurant with ID: " + id);
    }

    @Override
    public RestaurantDTO updateRestaurantScore(String id, int newScore) {
        rabbitProducerService.sendInfoMessage("Updating restaurant score for restaurant with ID: " + id);

        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);

        Double total = restaurant.getAverageRating() * restaurant.getTotalReviewNumber() + newScore;
        Long newTotalReviewNumber = restaurant.getTotalReviewNumber() + 1;

        restaurant.setTotalReviewNumber(newTotalReviewNumber);
        restaurant.setAverageRating(total / newTotalReviewNumber);

        restaurant = restaurantEntityService.save(restaurant);

        rabbitProducerService.sendInfoMessage("Updated restaurant score for restaurant with ID: " + id);

        return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
    }

    @Override
    public List<RestaurantDTO> getRestaurantsByNameContaining(String name) {
        rabbitProducerService.sendInfoMessage("Getting restaurants with name like: " + name);

        List<Restaurant> restaurantList = restaurantEntityService.getRestaurantsByNameContaining(name);

        if(restaurantList.isEmpty()) {
            throw new AppBusinessException(RestaurantErrorMessage.NO_RESTAURANTS_FOUND);
        }

        rabbitProducerService.sendInfoMessage("Retrieved " + restaurantList.size() + " restaurants with name like: " + name);

        return RestaurantMapper.INSTANCE.convertToRestaurantDTOs(restaurantList);
    }
}
