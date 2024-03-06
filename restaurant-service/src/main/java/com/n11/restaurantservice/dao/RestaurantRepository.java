package com.n11.restaurantservice.dao;

import com.n11.restaurantservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findRestaurantByName(String name);
    Optional<Restaurant> findRestaurantByEmail(String email);
}
