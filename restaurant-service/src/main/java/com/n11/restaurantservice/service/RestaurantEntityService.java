package com.n11.restaurantservice.service;

import com.n11.restaurantservice.dao.RestaurantRepository;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.general.BaseEntityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantEntityService extends BaseEntityService<Restaurant, RestaurantRepository> {
    protected RestaurantEntityService(RestaurantRepository repository) {
        super(repository);
    }

    public Optional<Restaurant> getRestaurantByName(String name) {
        return getRepository().findByName(name);
    }

    public List<Restaurant> getRestaurantsByNameContaining(String name) {
        return getRepository().findByNameContaining(name);
    }
}
