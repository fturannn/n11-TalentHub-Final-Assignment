package com.n11.userservice.client;

import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.general.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "restaurant", url = "http://localhost:8080/api/v1/restaurants")
public interface RestaurantServiceClient {

    @GetMapping
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getAllRestaurants();

    @PutMapping("/{id}/score")
    public void updateRestaurantScoreAccordingToUserReview(@PathVariable String id, @RequestBody Integer newScore);
}
