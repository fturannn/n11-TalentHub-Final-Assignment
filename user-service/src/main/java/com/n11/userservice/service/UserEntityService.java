package com.n11.userservice.service;

import com.n11.userservice.general.BaseEntityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService extends BaseEntityService {
    protected UserEntityService(JpaRepository repository) {
        super(repository);
    }
}
