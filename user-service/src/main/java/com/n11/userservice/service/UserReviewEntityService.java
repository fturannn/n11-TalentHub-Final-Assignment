package com.n11.userservice.service;

import com.n11.userservice.dao.UserReviewRepository;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.general.BaseEntityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserReviewEntityService extends BaseEntityService<UserReview, UserReviewRepository> {
    protected UserReviewEntityService(UserReviewRepository repository) {
        super(repository);
    }
}
