package com.n11.userservice.service;

import com.n11.userservice.dao.UserReviewRepository;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.general.BaseEntityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReviewEntityService extends BaseEntityService<UserReview, UserReviewRepository> {

    protected UserReviewEntityService(UserReviewRepository repository) {
        super(repository);
    }

    public List<UserReview> findReviewsByUserName(String name) {
        return getRepository().findByUserName(name);
    }

    public List<UserReview> findReviewsByUserId(Long id) {
        return getRepository().findByUserId(id);
    }
}
