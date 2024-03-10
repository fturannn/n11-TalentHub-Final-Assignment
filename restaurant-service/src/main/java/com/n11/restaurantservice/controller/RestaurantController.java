package com.n11.restaurantservice.controller;

import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.general.RestResponse;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantControllerContract restaurantControllerContract;

    @GetMapping
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getAllRestaurants(){
        List<RestaurantDTO> restaurantDTOS= restaurantControllerContract.getAll();
        return ResponseEntity.ok(RestResponse.of(restaurantDTOS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<RestaurantDTO>> getRestaurantById(@PathVariable Long id) {
        RestaurantDTO restaurantDTO = restaurantControllerContract.getById(id);
        return ResponseEntity.ok(RestResponse.of(restaurantDTO));
    }

    @PostMapping
    public ResponseEntity<RestResponse<RestaurantDTO>> saveRestaurant(@RequestBody @Valid RestaurantSaveRequest request) {
        RestaurantDTO restaurantDTO = restaurantControllerContract.save(request);
        return ResponseEntity.ok(RestResponse.of(restaurantDTO));
    }

    @PutMapping("/{debugRestaurantId}")
    public ResponseEntity<RestResponse<RestaurantDTO>> updateRestaurant(@PathVariable Long debugRestaurantId, @RequestBody RestaurantUpdateRequest request) {
        RestaurantDTO restaurantDTO = restaurantControllerContract.update(debugRestaurantId, request);
        return ResponseEntity.ok(RestResponse.of(restaurantDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantControllerContract.delete(id);
    }

    @PutMapping("/{id}/score")
    public RestaurantDTO updateRestaurantScore(@PathVariable Long id, @RequestBody int newScore) {
        return restaurantControllerContract.updateRestaurantScore(id, newScore);
    }
}
