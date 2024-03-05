package com.n11.userservice.service;

import com.n11.userservice.dao.UserRepository;
import com.n11.userservice.entity.User;
import com.n11.userservice.general.BaseEntityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityService extends BaseEntityService<User, UserRepository> {
    protected UserEntityService(UserRepository repository) {
        super(repository);
    }

    public Optional<User> getUserByEmail(String email) {
        return getRepository().findUserByEmail(email);
    }
}
