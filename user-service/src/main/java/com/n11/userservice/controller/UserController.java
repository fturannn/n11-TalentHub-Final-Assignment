package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Management")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserControllerContract userControllerContract;

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users.")
    public ResponseEntity<RestResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> userDTOS = userControllerContract.getAll();
        return ResponseEntity.ok(RestResponse.of(userDTOS));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves user details based on the provided ID.")
    public ResponseEntity<RestResponse<UserDTO>> getUserById(@PathVariable @NotNull Long id) {
        UserDTO userDTO = userControllerContract.getById(id);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @PostMapping
    @Operation(summary = "Save new user", description = "Saves a new user based on the provided request.")
    public ResponseEntity<RestResponse<UserDTO>> saveUser(@RequestBody @Valid UserSaveRequest request) {
        UserDTO userDTO = userControllerContract.save(request);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @PutMapping("/{debugUserId}")
    @Operation(summary = "Update existing user", description = "Updates an existing user based on the provided request and ID.")
    public ResponseEntity<RestResponse<UserDTO>> updateUser(@PathVariable @NotNull Long debugUserId,
                                                        @RequestBody @Valid UserUpdateRequest request) {
        UserDTO userDTO = userControllerContract.update(debugUserId, request);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes the user based on the provided ID.")
    public void deleteUser(@PathVariable @NotNull Long id) {
        userControllerContract.delete(id);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate user", description = "Activates the user based on the provided ID.")
    public ResponseEntity<RestResponse<UserDTO>> activateUser(@PathVariable @NotNull Long id) {
        UserDTO userDTO = userControllerContract.activate(id);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate user", description = "Deactivates the user based on the provided ID.")
    public ResponseEntity<RestResponse<UserDTO>> deactivateUser(@PathVariable @NotNull Long id) {
        UserDTO userDTO = userControllerContract.deactivate(id);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @GetMapping("/recommended-restaurants/{id}")
    @Operation(summary = "Get recommended restaurants for user", description = "Retrieves recommended restaurants for the user based on the provided ID.")
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getRecommendedRestaurants(@PathVariable @NotNull Long id) {
        List<RestaurantDTO> restaurantDTOS = userControllerContract.getRecommendedRestaurants(id);
        return ResponseEntity.ok(RestResponse.of(restaurantDTOS));
    }
}
