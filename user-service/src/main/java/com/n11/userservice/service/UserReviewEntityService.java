package com.n11.userservice.service;

import com.n11.userservice.general.BaseEntityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserReviewEntityService extends BaseEntityService {
    protected UserReviewEntityService(JpaRepository repository) {
        super(repository);
    }
}
