package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.faker.RestaurantDTOFaker;
import com.n11.userservice.faker.UserDTOFaker;
import com.n11.userservice.faker.UserSaveRequestFaker;
import com.n11.userservice.faker.UserUpdateRequestFaker;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
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
class UserControllerUnitTest {

    @Mock
    UserControllerContract userControllerContract;

    @InjectMocks
    UserController userController;

    @Test
    void shouldGetAllUsers() {
        //given
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        List<UserDTO> userDTOList = userDTOFaker.userDTOList();

        //when
        Mockito.when(userControllerContract.getAll()).thenReturn(userDTOList);

        ResponseEntity<RestResponse<List<UserDTO>>> response = userController.getAllUsers();
        List<UserDTO> results = response.getBody().getData();

        //then
        assertEquals(userDTOList, results);
        assertEquals(userDTOList.size(), results.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        for (int i = 0; i < results.size(); i++) {
            UserDTO result = results.get(i);
            UserDTO userDTO = userDTOList.get(i);

            assertEquals(userDTO.id(), result.id());
            assertEquals(userDTO.name(), result.name());
            assertEquals(userDTO.surname(), result.surname());
            assertEquals(userDTO.country(), result.country());
            assertEquals(userDTO.city(), result.city());
            assertEquals(userDTO.district(), result.district());
            assertEquals(userDTO.reviewCount(), result.reviewCount());
        }

        InOrder inOrder = Mockito.inOrder(userControllerContract);
        inOrder.verify(userControllerContract).getAll();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldGetUserById() {
        //given
        Long id = 5L;

        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        //when
        Mockito.when(userControllerContract.getById(Mockito.anyLong())).thenReturn(userDTO);

        ResponseEntity<RestResponse<UserDTO>> response = userController.getUserById(id);
        UserDTO result = response.getBody().getData();

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, result);
        assertEquals(userDTO.id(), result.id());
        assertEquals(userDTO.name(), result.name());
        assertEquals(userDTO.surname(), result.surname());
        assertEquals(userDTO.country(), result.country());
        assertEquals(userDTO.city(), result.city());
        assertEquals(userDTO.district(), result.district());
        assertEquals(userDTO.reviewCount(), result.reviewCount());

        InOrder inOrder = Mockito.inOrder(userControllerContract);
        inOrder.verify(userControllerContract).getById(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldSaveUser() {
        //given
        UserSaveRequestFaker userSaveRequestFaker = new UserSaveRequestFaker();
        UserSaveRequest request = userSaveRequestFaker.userSaveRequest();
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        //when
        Mockito.when(userControllerContract.save(Mockito.any(UserSaveRequest.class))).thenReturn(userDTO);

        ResponseEntity<RestResponse<UserDTO>> response = userController.saveUser(request);
        UserDTO result = response.getBody().getData();

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, result);
        assertEquals(userDTO.id(), result.id());
        assertEquals(userDTO.name(), result.name());
        assertEquals(userDTO.surname(), result.surname());
        assertEquals(userDTO.country(), result.country());
        assertEquals(userDTO.city(), result.city());
        assertEquals(userDTO.district(), result.district());
        assertEquals(userDTO.reviewCount(), result.reviewCount());

        InOrder inOrder = Mockito.inOrder(userControllerContract);
        inOrder.verify(userControllerContract).save(request);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldUpdateUser() {
        //given
        Long id = 3L;
        UserUpdateRequestFaker userUpdateRequestFaker = new UserUpdateRequestFaker();
        UserUpdateRequest userUpdateRequest = userUpdateRequestFaker.userUpdateRequest();
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        //when
        Mockito.when(userControllerContract.update(Mockito.anyLong(), Mockito.any(UserUpdateRequest.class)))
                .thenReturn(userDTO);

        ResponseEntity<RestResponse<UserDTO>> response = userController.updateUser(id, userUpdateRequest);
        UserDTO result = response.getBody().getData();

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, result);
        assertEquals(userDTO.id(), result.id());
        assertEquals(userDTO.name(), result.name());
        assertEquals(userDTO.surname(), result.surname());
        assertEquals(userDTO.country(), result.country());
        assertEquals(userDTO.city(), result.city());
        assertEquals(userDTO.district(), result.district());
        assertEquals(userDTO.reviewCount(), result.reviewCount());

        InOrder inOrder = Mockito.inOrder(userControllerContract);
        inOrder.verify(userControllerContract).update(id, userUpdateRequest);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldDeleteUser() {
        //given
        Long id = 3L;

        //when
        Mockito.doNothing().when(userControllerContract).delete(Mockito.anyLong());

        userController.deleteUser(id);

        //then
        InOrder inOrder = Mockito.inOrder(userControllerContract);
        inOrder.verify(userControllerContract).delete(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldActivateUser() {
        //given
        Long id = 3L;
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        //when
        Mockito.when(userControllerContract.activate(Mockito.anyLong())).thenReturn(userDTO);

        ResponseEntity<RestResponse<UserDTO>> response = userController.activateUser(id);
        UserDTO result = response.getBody().getData();

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, result);
        assertEquals(userDTO.id(), result.id());
        assertEquals(userDTO.name(), result.name());
        assertEquals(userDTO.surname(), result.surname());
        assertEquals(userDTO.country(), result.country());
        assertEquals(userDTO.city(), result.city());
        assertEquals(userDTO.district(), result.district());
        assertEquals(userDTO.reviewCount(), result.reviewCount());

        InOrder inOrder = Mockito.inOrder(userControllerContract);
        inOrder.verify(userControllerContract).activate(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldDeactivateUser() {
        //given
        Long id = 3L;
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        //when
        Mockito.when(userControllerContract.deactivate(Mockito.anyLong())).thenReturn(userDTO);

        ResponseEntity<RestResponse<UserDTO>> response = userController.deactivateUser(id);
        UserDTO result = response.getBody().getData();

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, result);
        assertEquals(userDTO.id(), result.id());
        assertEquals(userDTO.name(), result.name());
        assertEquals(userDTO.surname(), result.surname());
        assertEquals(userDTO.country(), result.country());
        assertEquals(userDTO.city(), result.city());
        assertEquals(userDTO.district(), result.district());
        assertEquals(userDTO.reviewCount(), result.reviewCount());

        InOrder inOrder = Mockito.inOrder(userControllerContract);
        inOrder.verify(userControllerContract).deactivate(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldGetRecommendedRestaurants() {
        //given
        Long id = 3L;
        RestaurantDTOFaker restaurantDTOFaker = new RestaurantDTOFaker();
        List<RestaurantDTO> restaurantDTOList = restaurantDTOFaker.restaurantDTOList();

        //when
        Mockito.when(userControllerContract.getRecommendedRestaurants(Mockito.anyLong()))
                .thenReturn(restaurantDTOList);

        ResponseEntity<RestResponse<List<RestaurantDTO>>> response = userController.getRecommendedRestaurants(id);
        List<RestaurantDTO> results = response.getBody().getData();

        //then
        assertEquals(restaurantDTOList, results);
        assertEquals(restaurantDTOList.size(), results.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        for (int i = 0; i < results.size(); i++) {
            RestaurantDTO result = results.get(i);
            RestaurantDTO restaurantDTO = restaurantDTOList.get(i);

            assertEquals(restaurantDTO.getId(), result.getId());
            assertEquals(restaurantDTO.getName(), result.getName());
            assertEquals(restaurantDTO.getPhoneNumber(), result.getPhoneNumber());
            assertEquals(restaurantDTO.getEmail(), result.getEmail());
            assertEquals(restaurantDTO.getCountry(), result.getCountry());
            assertEquals(restaurantDTO.getCity(), result.getCity());
            assertEquals(restaurantDTO.getDistrict(), result.getDistrict());
            assertEquals(restaurantDTO.getLatitude(), result.getLatitude());
            assertEquals(restaurantDTO.getLongitude(), result.getLongitude());
            assertEquals(restaurantDTO.getOpeningHour(), result.getOpeningHour());
            assertEquals(restaurantDTO.getClosingHour(), result.getClosingHour());
            assertEquals(restaurantDTO.getCuisineType(), result.getCuisineType());
            assertEquals(restaurantDTO.getFeatures(), result.getFeatures());
            assertEquals(restaurantDTO.getTotalReviewNumber(), result.getTotalReviewNumber());
            assertEquals(restaurantDTO.getAverageRating(), result.getAverageRating());
            assertEquals(restaurantDTO.getDescription(), result.getDescription());
        }

        InOrder inOrder = Mockito.inOrder(userControllerContract);
        inOrder.verify(userControllerContract).getRecommendedRestaurants(id);
        inOrder.verifyNoMoreInteractions();
    }
}