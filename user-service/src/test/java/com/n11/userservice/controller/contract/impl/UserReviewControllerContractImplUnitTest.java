package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.client.RestaurantServiceClient;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.errormessage.UserErrorMessage;
import com.n11.userservice.errormessage.UserReviewErrorMessage;
import com.n11.userservice.faker.UserFaker;
import com.n11.userservice.faker.UserReviewFaker;
import com.n11.userservice.faker.UserReviewSaveRequestFaker;
import com.n11.userservice.faker.UserReviewUpdateRequestFaker;
import com.n11.userservice.general.AppBusinessException;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;
import com.n11.userservice.service.UserEntityService;
import com.n11.userservice.service.UserReviewEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserReviewControllerContractImplUnitTest {

    @Mock
    UserReviewEntityService userReviewEntityService;

    @Mock
    UserEntityService userEntityService;

    @Mock
    RestaurantServiceClient restaurantServiceClient;

    @InjectMocks
    UserReviewControllerContractImpl userReviewControllerContractImpl;

    @Test
    void shouldGetById() {
        //given
        Long id = 3L;
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        UserReview userReview = userReviewFaker.userReview();

        //when
        Mockito.when(userReviewEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(userReview);

        UserReviewDTO result = userReviewControllerContractImpl.getById(id);

        //then
        assertNotNull(result);
        assertEquals(userReview.getId(), result.id());
        assertEquals(userReview.getUser().getName(), result.userName());
        assertEquals(userReview.getUser().getSurname(), result.userSurname());
        assertEquals(userReview.getUser().getName() + " " + userReview.getUser().getSurname(), result.userFullName());
        assertEquals(userReview.getRestaurantId(), result.restaurantId());
        assertEquals(userReview.getText(), result.text());
        assertEquals(userReview.getReviewDate(), result.reviewDate());
        assertEquals(userReview.getScore(), result.score());

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findByIdWithControl(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldGetAll() {
        //given
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        List<UserReview> userReviewList = userReviewFaker.userReviewList();

        //when
        Mockito.when(userReviewEntityService.findAll()).thenReturn(userReviewList);

        List<UserReviewDTO> results = userReviewControllerContractImpl.getAll();

        //then
        assertEquals(userReviewList.size(), results.size());

        for(int i = 0; i < results.size(); i++) {
            UserReview userReview = userReviewList.get(i);
            UserReviewDTO result = results.get(i);

            assertEquals(userReview.getId(), result.id());
            assertEquals(userReview.getUser().getName(), result.userName());
            assertEquals(userReview.getUser().getSurname(), result.userSurname());
            assertEquals(userReview.getUser().getName() + " " + userReview.getUser().getSurname(), result.userFullName());
            assertEquals(userReview.getRestaurantId(), result.restaurantId());
            assertEquals(userReview.getText(), result.text());
            assertEquals(userReview.getReviewDate(), result.reviewDate());
            assertEquals(userReview.getScore(), result.score());
        }

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findAll();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldSave() {
        //given
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        UserReviewFaker userReviewFaker = new UserReviewFaker();
        UserReview userReview = userReviewFaker.userReview();

        UserReviewSaveRequestFaker userReviewSaveRequestFaker = new UserReviewSaveRequestFaker();
        UserReviewSaveRequest userReviewSaveRequest = userReviewSaveRequestFaker.userReviewSaveRequest();

        //when
        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);
        Mockito.doNothing().when(restaurantServiceClient).updateRestaurantScore(Mockito.anyLong(), Mockito.anyInt());
        Mockito.when(userReviewEntityService.save(Mockito.any(UserReview.class))).thenReturn(userReview);

        UserReviewDTO result = userReviewControllerContractImpl.save(userReviewSaveRequest);

        //then
        assertNotNull(result);
        assertEquals(userReview.getId(), result.id());
        assertEquals(userReview.getUser().getName(), result.userName());
        assertEquals(userReview.getUser().getSurname(), result.userSurname());
        assertEquals(userReview.getUser().getName() + " " + userReview.getUser().getSurname(), result.userFullName());
        assertEquals(userReview.getRestaurantId(), result.restaurantId());
        assertEquals(userReview.getText(), result.text());
        assertEquals(userReview.getReviewDate(), result.reviewDate());
        assertEquals(userReview.getScore(), result.score());

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).save(Mockito.any(UserReview.class));
        inOrder.verifyNoMoreInteractions();

        inOrder = Mockito.inOrder(userEntityService);
        inOrder.verify(userEntityService).findByIdWithControl(userReviewSaveRequest.userId());
        inOrder.verifyNoMoreInteractions();

        inOrder = Mockito.inOrder(restaurantServiceClient);
        inOrder.verify(restaurantServiceClient).updateRestaurantScore(Mockito.eq(userReview.getRestaurantId()), Mockito.eq(userReviewSaveRequest.score().getValue()));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldUpdate() {
        //given
        Long id = 3L;

        UserReviewUpdateRequestFaker userReviewUpdateRequestFaker = new UserReviewUpdateRequestFaker();
        UserReviewUpdateRequest userReviewUpdateRequest = userReviewUpdateRequestFaker.userReviewUpdateRequest();

        UserReviewFaker userReviewFaker = new UserReviewFaker();
        UserReview userReview = userReviewFaker.userReview();

        //when
        Mockito.when(userReviewEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(userReview);
        Mockito.when(userReviewEntityService.save(Mockito.any(UserReview.class))).thenReturn(userReview);

        UserReviewDTO result = userReviewControllerContractImpl.update(id, userReviewUpdateRequest);

        //then
        assertNotNull(result);
        assertEquals(userReview.getId(), result.id());
        assertEquals(userReview.getUser().getName(), result.userName());
        assertEquals(userReview.getUser().getSurname(), result.userSurname());
        assertEquals(userReview.getUser().getName() + " " + userReview.getUser().getSurname(), result.userFullName());
        assertEquals(userReview.getRestaurantId(), result.restaurantId());
        assertEquals(userReview.getText(), result.text());
        assertEquals(userReview.getReviewDate(), result.reviewDate());
        assertEquals(userReview.getScore(), result.score());

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findByIdWithControl(id);
        inOrder.verify(userReviewEntityService).save(userReview);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldDelete() {
        //given
        Long id = 3L;

        UserReviewFaker userReviewFaker = new UserReviewFaker();
        UserReview userReview = userReviewFaker.userReview();

        //when
        Mockito.when(userReviewEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(userReview);
        Mockito.doNothing().when(userReviewEntityService).delete(Mockito.any(UserReview.class));

        userReviewControllerContractImpl.delete(id);

        //then
        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findByIdWithControl(id);
        inOrder.verify(userReviewEntityService).delete(userReview);
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    void shouldGetReviewsByUserName() {
        //given
        String name = "Test Name";

        UserReviewFaker userReviewFaker = new UserReviewFaker();
        List<UserReview> userReviewList = userReviewFaker.userReviewList();

        //when
        Mockito.when(userReviewEntityService.findReviewsByUserName(Mockito.anyString())).thenReturn(userReviewList);

        List<UserReviewDTO> results = userReviewControllerContractImpl.getReviewsByUserName(name);

        //then
        assertEquals(userReviewList.size(), results.size());

        for(int i = 0; i < results.size(); i++) {
            UserReview userReview = userReviewList.get(i);
            UserReviewDTO result = results.get(i);

            assertEquals(userReview.getId(), result.id());
            assertEquals(userReview.getUser().getName(), result.userName());
            assertEquals(userReview.getUser().getSurname(), result.userSurname());
            assertEquals(userReview.getUser().getName() + " " + userReview.getUser().getSurname(), result.userFullName());
            assertEquals(userReview.getRestaurantId(), result.restaurantId());
            assertEquals(userReview.getText(), result.text());
            assertEquals(userReview.getReviewDate(), result.reviewDate());
            assertEquals(userReview.getScore(), result.score());
        }

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findReviewsByUserName(name);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldNotGetReviewsByUserNameWhenThereIsNoReview() {
        //given
        String name = "Test Name";
        List<UserReview> userReviewList = new ArrayList<>();

        //when
        Mockito.when(userReviewEntityService.findReviewsByUserName(Mockito.anyString())).thenReturn(userReviewList);

        //then
        AppBusinessException appBusinessException = assertThrows(AppBusinessException.class,
                () -> userReviewControllerContractImpl.getReviewsByUserName(name));
        assertEquals(UserReviewErrorMessage.REVIEW_NOT_FOUND, appBusinessException.getBaseErrorMessage());
    }

    @Test
    void shouldGetReviewsByUserId() {
        //given
        Long id = 3L;

        UserReviewFaker userReviewFaker = new UserReviewFaker();
        List<UserReview> userReviewList = userReviewFaker.userReviewList();

        //when
        Mockito.when(userReviewEntityService.findReviewsByUserId(Mockito.anyLong())).thenReturn(userReviewList);

        List<UserReviewDTO> results = userReviewControllerContractImpl.getReviewsByUserId(id);

        //then
        assertEquals(userReviewList.size(), results.size());

        for(int i = 0; i < results.size(); i++) {
            UserReview userReview = userReviewList.get(i);
            UserReviewDTO result = results.get(i);

            assertEquals(userReview.getId(), result.id());
            assertEquals(userReview.getUser().getName(), result.userName());
            assertEquals(userReview.getUser().getSurname(), result.userSurname());
            assertEquals(userReview.getUser().getName() + " " + userReview.getUser().getSurname(), result.userFullName());
            assertEquals(userReview.getRestaurantId(), result.restaurantId());
            assertEquals(userReview.getText(), result.text());
            assertEquals(userReview.getReviewDate(), result.reviewDate());
            assertEquals(userReview.getScore(), result.score());
        }

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findReviewsByUserId(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldNotGetReviewsByUserIdWhenThereIsNoReview() {
        //given
        Long id = 3L;
        List<UserReview> userReviewList = new ArrayList<>();

        //when
        Mockito.when(userReviewEntityService.findReviewsByUserId(Mockito.anyLong())).thenReturn(userReviewList);

        //then
        AppBusinessException appBusinessException = assertThrows(AppBusinessException.class,
                () -> userReviewControllerContractImpl.getReviewsByUserId(id));
        assertEquals(UserReviewErrorMessage.REVIEW_NOT_FOUND, appBusinessException.getBaseErrorMessage());
    }
}