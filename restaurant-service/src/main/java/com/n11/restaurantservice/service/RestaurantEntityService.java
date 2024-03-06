package com.n11.restaurantservice.service;

import com.n11.restaurantservice.dao.RestaurantRepository;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.general.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class RestaurantEntityService extends BaseEntityService<Restaurant, RestaurantRepository> {
    protected RestaurantEntityService(RestaurantRepository repository) {
        super(repository);
    }
}
