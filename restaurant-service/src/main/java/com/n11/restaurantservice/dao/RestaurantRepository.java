package com.n11.restaurantservice.dao;

import com.n11.restaurantservice.entity.Restaurant;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends SolrCrudRepository<Restaurant, String> {

    Optional<Restaurant> findByName(String name);

    List<Restaurant> findByNameContaining(String name);
}
