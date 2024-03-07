package com.n11.userservice.client;

import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.general.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "restaurant", url = "http://localhost:8080/api/v1/restaurants")
public interface RestaurantServiceClient {

    @GetMapping
    public ResponseEntity<RestResponse<RestaurantDTO>> getAllRestaurants();

    @PutMapping("/{id}/score")
    public void updateRestaurantScore(@PathVariable Long id, @RequestBody int newScore);
}
