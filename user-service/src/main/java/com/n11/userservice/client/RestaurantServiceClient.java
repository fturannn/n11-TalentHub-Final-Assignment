package com.n11.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "restaurant", url = "http://localhost:8080/api/v1/restaurants")
public interface RestaurantServiceClient {
    @PutMapping("/{id}/score")
    public void updateAverageRatingAndTotalReviewNumber(@PathVariable Long id, @RequestBody int newScore);
}
