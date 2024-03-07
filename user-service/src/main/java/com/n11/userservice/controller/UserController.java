package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserControllerContract userControllerContract;

    @GetMapping
    public ResponseEntity<RestResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> userDTOS = userControllerContract.getAll();
        return ResponseEntity.ok(RestResponse.of(userDTOS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UserDTO>> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userControllerContract.getById(id);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @PostMapping
    public ResponseEntity<RestResponse<UserDTO>> saveUser(@RequestBody UserSaveRequest request) {
        UserDTO userDTO = userControllerContract.save(request);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @PutMapping("/{debugUserId}")
    public ResponseEntity<RestResponse<UserDTO>> updateUser(@PathVariable Long debugUserId,
                                                        @RequestBody UserUpdateRequest request) {
        UserDTO userDTO = userControllerContract.update(debugUserId, request);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userControllerContract.delete(id);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<RestResponse<UserDTO>> activateUser(@PathVariable Long id) {
        UserDTO userDTO = userControllerContract.activate(id);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<RestResponse<UserDTO>> deactivateUser(@PathVariable Long id) {
        UserDTO userDTO = userControllerContract.deactivate(id);
        return ResponseEntity.ok(RestResponse.of(userDTO));
    }

    @GetMapping("/recommended/{id}")
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getRecommendedRestaurants(@PathVariable Long id) {
        List<RestaurantDTO> restaurantDTOS = userControllerContract.getRecommendedRestaurants(id);
        return ResponseEntity.ok(RestResponse.of(restaurantDTOS));
    }
}
