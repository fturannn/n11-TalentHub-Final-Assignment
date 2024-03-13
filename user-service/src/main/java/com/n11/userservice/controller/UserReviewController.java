package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;
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
@RequestMapping("/api/v1/reviews")
public class UserReviewController {

    private final UserReviewControllerContract userReviewControllerContract;

    @GetMapping
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getAllReviews() {
        List<UserReviewDTO> userReviewDTOS = userReviewControllerContract.getAll();
        return ResponseEntity.ok(RestResponse.of(userReviewDTOS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UserReviewDTO>> getReviewById(@PathVariable @NotNull Long id) {
        UserReviewDTO userReviewDTO = userReviewControllerContract.getById(id);
        return ResponseEntity.ok(RestResponse.of(userReviewDTO));
    }

    @PostMapping
    public ResponseEntity<RestResponse<UserReviewDTO>> saveReview(@RequestBody @Valid UserReviewSaveRequest request) {
        UserReviewDTO userReviewDTO = userReviewControllerContract.save(request);
        return ResponseEntity.ok(RestResponse.of(userReviewDTO));
    }

    @PutMapping("/{debugReviewId}")
    public ResponseEntity<RestResponse<UserReviewDTO>> updateReview(@PathVariable @NotNull Long debugReviewId,
                                                                    @RequestBody @Valid UserReviewUpdateRequest request) {
        UserReviewDTO userReviewDTO = userReviewControllerContract.update(debugReviewId, request);
        return ResponseEntity.ok(RestResponse.of(userReviewDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable @NotNull Long id) {
        userReviewControllerContract.delete(id);
    }

    @GetMapping("/with-user-name")
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getReviewsByUserName(@RequestParam @NotBlank String name) {
        List<UserReviewDTO> userReviewDTOS = userReviewControllerContract.getReviewsByUserName(name);
        return ResponseEntity.ok(RestResponse.of(userReviewDTOS));
    }

    @GetMapping("/with-user-id/{userId}")
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getReviewsByUserId(@PathVariable @NotNull Long userId) {
        List<UserReviewDTO> userReviewDTOS = userReviewControllerContract.getReviewsByUserId(userId);
        return ResponseEntity.ok(RestResponse.of(userReviewDTOS));
    }
}
