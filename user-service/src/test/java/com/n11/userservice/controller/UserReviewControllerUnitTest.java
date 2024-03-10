package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.faker.UserReviewDTOFaker;
import com.n11.userservice.faker.UserReviewSaveRequestFaker;
import com.n11.userservice.faker.UserReviewUpdateRequestFaker;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserReviewControllerUnitTest {

    @Mock
    UserReviewControllerContract userReviewControllerContract;

    @InjectMocks
    UserReviewController userReviewController;

    @Test
    void shouldGetAllReviews() {
        //given
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        List<UserReviewDTO> userReviewDTOList = userReviewDTOFaker.userReviewDTOList();

        //when
        Mockito.when(userReviewControllerContract.getAll()).thenReturn(userReviewDTOList);

        ResponseEntity<RestResponse<List<UserReviewDTO>>> response = userReviewController.getAllReviews();
        List<UserReviewDTO> results = response.getBody().getData();

        //then
        assertEquals(userReviewDTOList, results);
        assertEquals(userReviewDTOList.size(), results.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        for (int i = 0; i < results.size(); i++) {
            UserReviewDTO result = results.get(i);
            UserReviewDTO userReviewDTO = userReviewDTOList.get(i);

            assertEquals(userReviewDTO.id(), result.id());
            assertEquals(userReviewDTO.userName(), result.userName());
            assertEquals(userReviewDTO.userSurname(), result.userSurname());
            assertEquals(userReviewDTO.userFullName(), result.userFullName());
            assertEquals(userReviewDTO.restaurantId(), result.restaurantId());
            assertEquals(userReviewDTO.text(), result.text());
            assertEquals(userReviewDTO.reviewDate(), result.reviewDate());
            assertEquals(userReviewDTO.score(), result.score());
        }

        InOrder inOrder = Mockito.inOrder(userReviewControllerContract);
        inOrder.verify(userReviewControllerContract).getAll();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldGetReviewById() {
        //given
        Long id = 3L;
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        UserReviewDTO userReviewDTO = userReviewDTOFaker.userReviewDTO();

        //when
        Mockito.when(userReviewControllerContract.getById(Mockito.anyLong())).thenReturn(userReviewDTO);

        ResponseEntity<RestResponse<UserReviewDTO>> response = userReviewController.getReviewById(id);
        UserReviewDTO result = response.getBody().getData();

        //then
        assertEquals(userReviewDTO, result);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userReviewDTO.id(), result.id());
        assertEquals(userReviewDTO.userName(), result.userName());
        assertEquals(userReviewDTO.userSurname(), result.userSurname());
        assertEquals(userReviewDTO.userFullName(), result.userFullName());
        assertEquals(userReviewDTO.restaurantId(), result.restaurantId());
        assertEquals(userReviewDTO.text(), result.text());
        assertEquals(userReviewDTO.reviewDate(), result.reviewDate());
        assertEquals(userReviewDTO.score(), result.score());

        InOrder inOrder = Mockito.inOrder(userReviewControllerContract);
        inOrder.verify(userReviewControllerContract).getById(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldSaveReview() {
        //given
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        UserReviewDTO userReviewDTO = userReviewDTOFaker.userReviewDTO();
        UserReviewSaveRequestFaker userReviewSaveRequestFaker = new UserReviewSaveRequestFaker();
        UserReviewSaveRequest userReviewSaveRequest = userReviewSaveRequestFaker.userReviewSaveRequest();

        //when
        Mockito.when(userReviewControllerContract.save(Mockito.any(UserReviewSaveRequest.class))).thenReturn(userReviewDTO);

        ResponseEntity<RestResponse<UserReviewDTO>> response = userReviewController.saveReview(userReviewSaveRequest);
        UserReviewDTO result = response.getBody().getData();

        //then
        assertEquals(userReviewDTO, result);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userReviewDTO.id(), result.id());
        assertEquals(userReviewDTO.userName(), result.userName());
        assertEquals(userReviewDTO.userSurname(), result.userSurname());
        assertEquals(userReviewDTO.userFullName(), result.userFullName());
        assertEquals(userReviewDTO.restaurantId(), result.restaurantId());
        assertEquals(userReviewDTO.text(), result.text());
        assertEquals(userReviewDTO.reviewDate(), result.reviewDate());
        assertEquals(userReviewDTO.score(), result.score());

        InOrder inOrder = Mockito.inOrder(userReviewControllerContract);
        inOrder.verify(userReviewControllerContract).save(userReviewSaveRequest);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldUpdateReview() {
        //given
        Long id = 3L;
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        UserReviewDTO userReviewDTO = userReviewDTOFaker.userReviewDTO();
        UserReviewUpdateRequestFaker userReviewUpdateRequestFaker = new UserReviewUpdateRequestFaker();
        UserReviewUpdateRequest userReviewUpdateRequest = userReviewUpdateRequestFaker.userReviewUpdateRequest();

        //when
        Mockito.when(userReviewControllerContract.update(Mockito.anyLong(), Mockito.any(UserReviewUpdateRequest.class)))
                .thenReturn(userReviewDTO);

        ResponseEntity<RestResponse<UserReviewDTO>> response = userReviewController.updateReview(id, userReviewUpdateRequest);
        UserReviewDTO result = response.getBody().getData();

        //then
        assertEquals(userReviewDTO, result);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userReviewDTO.id(), result.id());
        assertEquals(userReviewDTO.userName(), result.userName());
        assertEquals(userReviewDTO.userSurname(), result.userSurname());
        assertEquals(userReviewDTO.userFullName(), result.userFullName());
        assertEquals(userReviewDTO.restaurantId(), result.restaurantId());
        assertEquals(userReviewDTO.text(), result.text());
        assertEquals(userReviewDTO.reviewDate(), result.reviewDate());
        assertEquals(userReviewDTO.score(), result.score());

        InOrder inOrder = Mockito.inOrder(userReviewControllerContract);
        inOrder.verify(userReviewControllerContract).update(id, userReviewUpdateRequest);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shoulDeleteReview() {
        //given
        Long id = 3L;

        //when
        Mockito.doNothing().when(userReviewControllerContract).delete(Mockito.anyLong());

        userReviewController.deleteReview(id);

        //then
        InOrder inOrder = Mockito.inOrder(userReviewControllerContract);
        inOrder.verify(userReviewControllerContract).delete(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldGetReviewsByUserName() {
        //given
        String userName = "Test User Name";
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        List<UserReviewDTO> userReviewDTOList = userReviewDTOFaker.userReviewDTOList();

        //when
        Mockito.when(userReviewControllerContract.getReviewsByUserName(Mockito.anyString())).thenReturn(userReviewDTOList);

        ResponseEntity<RestResponse<List<UserReviewDTO>>> response = userReviewController.getReviewsByUserName(userName);
        List<UserReviewDTO> results = response.getBody().getData();

        //then
        assertEquals(userReviewDTOList, results);
        assertEquals(userReviewDTOList.size(), results.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        for (int i = 0; i < results.size(); i++) {
            UserReviewDTO result = results.get(i);
            UserReviewDTO userReviewDTO = userReviewDTOList.get(i);

            assertEquals(userReviewDTO.id(), result.id());
            assertEquals(userReviewDTO.userName(), result.userName());
            assertEquals(userReviewDTO.userSurname(), result.userSurname());
            assertEquals(userReviewDTO.userFullName(), result.userFullName());
            assertEquals(userReviewDTO.restaurantId(), result.restaurantId());
            assertEquals(userReviewDTO.text(), result.text());
            assertEquals(userReviewDTO.reviewDate(), result.reviewDate());
            assertEquals(userReviewDTO.score(), result.score());
        }

        InOrder inOrder = Mockito.inOrder(userReviewControllerContract);
        inOrder.verify(userReviewControllerContract).getReviewsByUserName(userName);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldGetReviewsByUserId() {
        //given
        Long userId = 3L;
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        List<UserReviewDTO> userReviewDTOList = userReviewDTOFaker.userReviewDTOList();

        //when
        Mockito.when(userReviewControllerContract.getReviewsByUserId(Mockito.anyLong())).thenReturn(userReviewDTOList);

        ResponseEntity<RestResponse<List<UserReviewDTO>>> response = userReviewController.getReviewsByUserId(userId);
        List<UserReviewDTO> results = response.getBody().getData();

        //then
        assertEquals(userReviewDTOList, results);
        assertEquals(userReviewDTOList.size(), results.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        for (int i = 0; i < results.size(); i++) {
            UserReviewDTO result = results.get(i);
            UserReviewDTO userReviewDTO = userReviewDTOList.get(i);

            assertEquals(userReviewDTO.id(), result.id());
            assertEquals(userReviewDTO.userName(), result.userName());
            assertEquals(userReviewDTO.userSurname(), result.userSurname());
            assertEquals(userReviewDTO.userFullName(), result.userFullName());
            assertEquals(userReviewDTO.restaurantId(), result.restaurantId());
            assertEquals(userReviewDTO.text(), result.text());
            assertEquals(userReviewDTO.reviewDate(), result.reviewDate());
            assertEquals(userReviewDTO.score(), result.score());
        }

        InOrder inOrder = Mockito.inOrder(userReviewControllerContract);
        inOrder.verify(userReviewControllerContract).getReviewsByUserId(userId);
        inOrder.verifyNoMoreInteractions();
    }
}