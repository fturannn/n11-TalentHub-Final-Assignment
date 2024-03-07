package com.n11.userservice.controller.contract;

import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;

import java.util.List;

public interface UserReviewControllerContract {

    UserReviewDTO getById(Long id);
    List<UserReviewDTO> getAll();
    UserReviewDTO save(UserReviewSaveRequest request);
    UserReviewDTO update(Long id, UserReviewUpdateRequest request);
    void delete(Long id);
    List<UserReviewDTO> getReviewsByUserName(String name);
    List<UserReviewDTO> getReviewsByUserId(Long id);
}
