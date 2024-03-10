package com.n11.userservice.service;

import com.n11.userservice.dao.UserRepository;
import com.n11.userservice.entity.User;
import com.n11.userservice.faker.UserFaker;
import com.n11.userservice.service.UserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserEntityServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserEntityService userEntityService;

    @Test
    void shouldGetUserByEmail() {
        //given
        String email = "test@email.com";

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        //when
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        Optional<User> result = userEntityService.getUserByEmail(email);

        //then
        assertEquals(Optional.of(user), result);

        InOrder inOrder = Mockito.inOrder(userRepository);
        inOrder.verify(userRepository).findUserByEmail(Mockito.anyString());
        inOrder.verifyNoMoreInteractions();
    }
}