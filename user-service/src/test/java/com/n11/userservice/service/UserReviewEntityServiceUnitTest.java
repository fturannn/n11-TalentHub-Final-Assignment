package com.n11.userservice.service;

import com.n11.userservice.dao.UserReviewRepository;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.faker.UserReviewFaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserReviewEntityServiceUnitTest {

    @Mock
    private UserReviewRepository userReviewRepository;

    @InjectMocks
    private UserReviewEntityService userReviewEntityService;

    @Test
    void shouldFindReviewsByUserName() {
        //given
        String userName = "Test User Name";
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        List<UserReview> userReviewList = userReviewFaker.userReviewList();

        //when
        Mockito.when(userReviewRepository.findByUserName(Mockito.anyString())).thenReturn(userReviewList);

        List<UserReview> results = userReviewEntityService.findReviewsByUserName(userName);

        //then
        assertEquals(userReviewList, results);

        InOrder inOrder = Mockito.inOrder(userReviewRepository);
        inOrder.verify(userReviewRepository).findByUserName(userName);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldFindReviewsByUserId() {
        //given
        Long userId = 1L;
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        List<UserReview> userReviewList = userReviewFaker.userReviewList();

        //when
        Mockito.when(userReviewRepository.findByUserId(Mockito.anyLong())).thenReturn(userReviewList);

        List<UserReview> results = userReviewEntityService.findReviewsByUserId(userId);

        //then
        assertEquals(userReviewList, results);

        InOrder inOrder = Mockito.inOrder(userReviewRepository);
        inOrder.verify(userReviewRepository).findByUserId(userId);
        inOrder.verifyNoMoreInteractions();
    }
}