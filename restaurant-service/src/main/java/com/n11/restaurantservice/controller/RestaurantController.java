package com.n11.restaurantservice.controller;

import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.general.RestResponse;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@Tag(name = "Restaurant Controller", description = "Restaurant Management")
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantControllerContract restaurantControllerContract;

    @GetMapping
    @Operation(summary = "Get all restaurants", description = "Retrieves all active restaurants")
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getAllRestaurants(){
        List<RestaurantDTO> restaurantDTOS= restaurantControllerContract.getAll();
        return ResponseEntity.ok(RestResponse.of(restaurantDTOS));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by ID", description = "Returns the restaurant details based on the provided ID.")
    public ResponseEntity<RestResponse<RestaurantDTO>> getRestaurantById(@PathVariable @NotBlank String id) {
        RestaurantDTO restaurantDTO = restaurantControllerContract.getById(id);
        return ResponseEntity.ok(RestResponse.of(restaurantDTO));
    }

    @PostMapping
    @Operation(summary = "Save new restaurant", description = "Saves a new restaurant based on the provided request.")
    public ResponseEntity<RestResponse<RestaurantDTO>> saveRestaurant(@RequestBody @Valid RestaurantSaveRequest request) {
        RestaurantDTO restaurantDTO = restaurantControllerContract.save(request);
        return ResponseEntity.ok(RestResponse.of(restaurantDTO));
    }

    @PutMapping("/{debugRestaurantId}")
    @Operation(summary = "Update existing restaurant", description = "Updates an existing restaurant based on the provided request and ID.")
    public ResponseEntity<RestResponse<RestaurantDTO>> updateRestaurant(@PathVariable @NotBlank String debugRestaurantId, @RequestBody @Valid RestaurantUpdateRequest request) {
        RestaurantDTO restaurantDTO = restaurantControllerContract.update(debugRestaurantId, request);
        return ResponseEntity.ok(RestResponse.of(restaurantDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete restaurant", description = "Deletes the restaurant based on the provided ID.")
    public void deleteRestaurant(@PathVariable @NotBlank String id) {
        restaurantControllerContract.delete(id);
    }

    @PutMapping("/{id}/score")
    @Operation(summary = "Update restaurant score", description = "Updates the score of the restaurant based on the provided ID and new score.")
    public RestaurantDTO updateRestaurantScoreAccordingToUserReview(@PathVariable @NotBlank String id, @RequestBody @NotNull int newScore) {
        return restaurantControllerContract.updateRestaurantScore(id, newScore);
    }

    @GetMapping("/restaurant-name-like/{name}")
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getRestaurantsByNameContaining(@PathVariable @NotBlank String name) {
        List<RestaurantDTO> restaurantDTOS = restaurantControllerContract.getRestaurantsByNameContaining(name);
        return ResponseEntity.ok(RestResponse.of(restaurantDTOS));
    }
}
