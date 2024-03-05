package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class UserReviewController {

    private final UserReviewControllerContract userReviewControllerContract;

    @GetMapping
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getAllReviews() {
        List<UserReviewDTO> userReviewDTOS = userReviewControllerContract.getAll();
        return ResponseEntity.ok(RestResponse.of(userReviewDTOS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UserReviewDTO>> getReviewById(@PathVariable Long id) {
        UserReviewDTO userReviewDTO = userReviewControllerContract.getById(id);
        return ResponseEntity.ok(RestResponse.of(userReviewDTO));
    }

    @PostMapping
    public ResponseEntity<RestResponse<UserReviewDTO>> saveReview(@RequestBody UserReviewSaveRequest request) {
        UserReviewDTO userReviewDTO = userReviewControllerContract.save(request);
        return ResponseEntity.ok(RestResponse.of(userReviewDTO));
    }

    @PutMapping("/{debugReviewId}")
    public ResponseEntity<RestResponse<UserReviewDTO>> updateReview(@PathVariable Long debugReviewId,
                                                                    @RequestBody UserReviewUpdateRequest request) {
        UserReviewDTO userReviewDTO = userReviewControllerContract.update(debugReviewId, request);
        return ResponseEntity.ok(RestResponse.of(userReviewDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        userReviewControllerContract.delete(id);
    }
}
