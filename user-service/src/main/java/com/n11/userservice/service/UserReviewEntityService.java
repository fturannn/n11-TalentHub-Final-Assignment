package com.n11.userservice.service;

import com.n11.userservice.client.RestaurantServiceClient;
import com.n11.userservice.dao.UserReviewRepository;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.general.BaseEntityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReviewEntityService extends BaseEntityService<UserReview, UserReviewRepository> {

    private final RestaurantServiceClient restaurantServiceClient;
    protected UserReviewEntityService(UserReviewRepository repository, RestaurantServiceClient restaurantServiceClient) {
        super(repository);
        this.restaurantServiceClient = restaurantServiceClient;
    }

    public List<UserReview> findReviewsByUserName(String name) {
        return getRepository().findByUserName(name);
    }

    public List<UserReview> findReviewsByUserId(Long id) {
        return getRepository().findByUserId(id);
    }

    public void updateRestaurantScore(Long id, int newScore) {
        restaurantServiceClient.updateRestaurantScore(id, newScore);
    }
}
