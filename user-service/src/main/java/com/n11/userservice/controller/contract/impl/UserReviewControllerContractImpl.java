package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.client.RestaurantServiceClient;
import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.errormessage.UserReviewErrorMessage;
import com.n11.userservice.general.AppBusinessException;
import com.n11.userservice.mapper.UserReviewMapper;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;
import com.n11.userservice.service.UserEntityService;
import com.n11.userservice.service.UserReviewEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReviewControllerContractImpl implements UserReviewControllerContract {

    private final UserReviewEntityService userReviewEntityService;
    private final UserEntityService userEntityService;
    private final RestaurantServiceClient restaurantServiceClient;

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

        userReview.setReviewDate(LocalDateTime.now());
        userReview.setUser(userEntityService.findByIdWithControl(request.userId()));
        userReview.getUser().setReviewCount(userReview.getUser().getReviewCount() + 1);

        restaurantServiceClient.updateRestaurantScore(userReview.getRestaurantId(), request.score().getValue());

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

    @Override
    public List<UserReviewDTO> getReviewsByUserName(String name) {
        List<UserReview> userReviews = userReviewEntityService.findReviewsByUserName(name);

        if(userReviews.isEmpty()) {
            throw new AppBusinessException(UserReviewErrorMessage.REVIEW_NOT_FOUND);
        }

        return UserReviewMapper.INSTANCE.convertToUserReviewDTOs(userReviews);
    }

    @Override
    public List<UserReviewDTO> getReviewsByUserId(Long id) {
        List<UserReview> userReviews = userReviewEntityService.findReviewsByUserId(id);

        if(userReviews.isEmpty()) {
            throw new AppBusinessException(UserReviewErrorMessage.REVIEW_NOT_FOUND);
        }

        return UserReviewMapper.INSTANCE.convertToUserReviewDTOs(userReviews);
    }

}
