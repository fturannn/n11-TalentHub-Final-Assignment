package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.client.RestaurantServiceClient;
import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.errormessage.UserReviewErrorMessage;
import com.n11.userservice.general.AppBusinessException;
import com.n11.userservice.general.RabbitProducerService;
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
    private final RabbitProducerService rabbitProducerService;

    @Override
    public UserReviewDTO getById(Long id) {
        rabbitProducerService.sendInfoMessage("Getting user review by ID: " + id);

        UserReview userReview = userReviewEntityService.findByIdWithControl(id);

        rabbitProducerService.sendInfoMessage("Retrieved user review by ID: " + id);

        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(userReview);
    }

    @Override
    public List<UserReviewDTO> getAll() {
        rabbitProducerService.sendInfoMessage("Getting all user reviews");

        List<UserReview> userReviewList = userReviewEntityService.findAll();

        if(userReviewList.isEmpty()) {
            throw new AppBusinessException(UserReviewErrorMessage.NO_REVIEWS_AVAILABLE);
        }

        rabbitProducerService.sendInfoMessage("Retrieved " + userReviewList.size() + " user reviews");

        return UserReviewMapper.INSTANCE.convertToUserReviewDTOs(userReviewList);
    }

    @Override
    public UserReviewDTO save(UserReviewSaveRequest request) {
        rabbitProducerService.sendInfoMessage("Saving new user review");

        UserReview userReview = UserReviewMapper.INSTANCE.convertToUserReview(request);

        userReview.setReviewDate(LocalDateTime.now());
        userReview.setUser(userEntityService.findByIdWithControl(request.userId()));
        userReview.getUser().setReviewCount(userReview.getUser().getReviewCount() + 1);

        restaurantServiceClient.updateRestaurantScoreAccordingToUserReview(userReview.getRestaurantId(), request.score().getValue());

        userReview = userReviewEntityService.save(userReview);

        rabbitProducerService.sendInfoMessage("Saved new user review");

        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(userReview);
    }

    @Override
    public UserReviewDTO update(Long id, UserReviewUpdateRequest request) {
        rabbitProducerService.sendInfoMessage("Updating user review with ID: " + id);

        UserReview userReview = userReviewEntityService.findByIdWithControl(id);

        UserReviewMapper.INSTANCE.updateUserReviewFields(userReview, request);

        userReview = userReviewEntityService.save(userReview);

        rabbitProducerService.sendInfoMessage("Updated user review with ID: " + id);

        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(userReview);
    }

    @Override
    public void delete(Long id) {
        rabbitProducerService.sendInfoMessage("Deleting user review with ID: " + id);

        UserReview userReview = userReviewEntityService.findByIdWithControl(id);

        rabbitProducerService.sendInfoMessage("Deleted user review with ID: " + id);

        userReviewEntityService.delete(userReview);
    }

    @Override
    public List<UserReviewDTO> getReviewsByUserName(String name) {
        rabbitProducerService.sendInfoMessage("Getting user reviews by user name: " + name);

        List<UserReview> userReviews = userReviewEntityService.findReviewsByUserName(name);

        if(userReviews.isEmpty()) {
            throw new AppBusinessException(UserReviewErrorMessage.REVIEW_NOT_FOUND);
        }

        rabbitProducerService.sendInfoMessage("Retrieved " + userReviews.size() + " reviews by user name: " + name);

        return UserReviewMapper.INSTANCE.convertToUserReviewDTOs(userReviews);
    }

    @Override
    public List<UserReviewDTO> getReviewsByUserId(Long id) {
        rabbitProducerService.sendInfoMessage("Getting user reviews by user ID: " + id);

        List<UserReview> userReviews = userReviewEntityService.findReviewsByUserId(id);

        if(userReviews.isEmpty()) {
            throw new AppBusinessException(UserReviewErrorMessage.REVIEW_NOT_FOUND);
        }

        rabbitProducerService.sendInfoMessage("Retrieved " + userReviews.size() + " reviews by user ID: " + id);

        return UserReviewMapper.INSTANCE.convertToUserReviewDTOs(userReviews);
    }

    @Override
    public List<UserReviewDTO> getReviewsByRestaurantId(String id) {
        rabbitProducerService.sendInfoMessage("Getting user reviews by restaurant ID: " + id);

        List<UserReview> userReviews = userReviewEntityService.findReviewsByRestaurantId(id);

        if(userReviews.isEmpty()) {
            throw new AppBusinessException(UserReviewErrorMessage.REVIEW_NOT_FOUND);
        }

        rabbitProducerService.sendInfoMessage("Retrieved " + userReviews.size() + " reviews by restaurant ID: " + id);

        return UserReviewMapper.INSTANCE.convertToUserReviewDTOs(userReviews);
    }
}
