package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.mapper.UserReviewMapper;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;
import com.n11.userservice.service.UserReviewEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReviewControllerContractImpl implements UserReviewControllerContract {

    private final UserReviewEntityService userReviewEntityService;
    @Override
    public UserReviewDTO getById(Long id) {
        UserReview userReview = userReviewEntityService.findByIdWithControl(id);

        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(userReview);
    }

    @Override
    public List<UserReviewDTO> getAll() {
        List<UserReview> userReviewList = userReviewEntityService.findAll();

        return UserReviewMapper.INSTANCE.convertToUserReviewDTOs(userReviewList);
    }

    @Override
    public UserReviewDTO save(UserReviewSaveRequest request) {
        UserReview userReview = UserReviewMapper.INSTANCE.convertToUserReview(request);

        userReview = userReviewEntityService.save(userReview);

        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(userReview);
    }

    @Override
    public UserReviewDTO update(Long id, UserReviewUpdateRequest request) {
        UserReview userReview = userReviewEntityService.findByIdWithControl(id);

        UserReviewMapper.INSTANCE.updateUserReviewFields(userReview, request);

        userReview = userReviewEntityService.save(userReview);

        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(userReview);
    }

    @Override
    public void delete(Long id) {
        UserReview userReview = userReviewEntityService.findByIdWithControl(id);

        userReviewEntityService.delete(userReview);
    }
}
