package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "User Review Controller", description = "User Review Management")
@RequestMapping("/api/v1/reviews")
public class UserReviewController {

    private final UserReviewControllerContract userReviewControllerContract;

    @GetMapping
    @Operation(summary = "Get all reviews", description = "Get a list of all user reviews")
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getAllReviews() {
        List<UserReviewDTO> userReviewDTOS = userReviewControllerContract.getAll();
        return ResponseEntity.ok(RestResponse.of(userReviewDTOS));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID", description = "Get a user review by its ID")
    public ResponseEntity<RestResponse<UserReviewDTO>> getReviewById(@PathVariable @NotNull Long id) {
        UserReviewDTO userReviewDTO = userReviewControllerContract.getById(id);
        return ResponseEntity.ok(RestResponse.of(userReviewDTO));
    }

    @PostMapping
    @Operation(summary = "Save a new review", description = "Create a new user review")
    public ResponseEntity<RestResponse<UserReviewDTO>> saveReview(@RequestBody @Valid UserReviewSaveRequest request) {
        UserReviewDTO userReviewDTO = userReviewControllerContract.save(request);
        return ResponseEntity.ok(RestResponse.of(userReviewDTO));
    }

    @PutMapping("/{debugReviewId}")
    @Operation(summary = "Update review by ID", description = "Update an existing user review")
    public ResponseEntity<RestResponse<UserReviewDTO>> updateReview(@PathVariable @NotNull Long debugReviewId,
                                                                    @RequestBody @Valid UserReviewUpdateRequest request) {
        UserReviewDTO userReviewDTO = userReviewControllerContract.update(debugReviewId, request);
        return ResponseEntity.ok(RestResponse.of(userReviewDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review by ID", description = "Delete a user review by its ID")
    public void deleteReview(@PathVariable @NotNull Long id) {
        userReviewControllerContract.delete(id);
    }

    @GetMapping("/with-user-name")
    @Operation(summary = "Get reviews by user name", description = "Get a list of user reviews by user name")
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getReviewsByUserName(@RequestParam @NotBlank String name) {
        List<UserReviewDTO> userReviewDTOS = userReviewControllerContract.getReviewsByUserName(name);
        return ResponseEntity.ok(RestResponse.of(userReviewDTOS));
    }

    @GetMapping("/with-user-id/{userId}")
    @Operation(summary = "Get reviews by user ID", description = "Get a list of user reviews by user ID")
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getReviewsByUserId(@PathVariable @NotNull Long userId) {
        List<UserReviewDTO> userReviewDTOS = userReviewControllerContract.getReviewsByUserId(userId);
        return ResponseEntity.ok(RestResponse.of(userReviewDTOS));
    }

    @GetMapping("/with-restaurant-id/{restaurantId}")
    @Operation(summary = "Get reviews by restaurant ID", description = "Get a list of user reviews by restaurant ID")
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getReviewsByRestaurantId(@PathVariable @NotBlank String restaurantId) {
        List<UserReviewDTO> userReviewDTOS = userReviewControllerContract.getReviewsByRestaurantId(restaurantId);
        return ResponseEntity.ok(RestResponse.of(userReviewDTOS));
    }
}
