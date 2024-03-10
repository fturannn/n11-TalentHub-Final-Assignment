package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.client.RestaurantServiceClient;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumStatus;
import com.n11.userservice.errormessage.UserErrorMessage;
import com.n11.userservice.faker.*;
import com.n11.userservice.general.AppBusinessException;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
import com.n11.userservice.service.UserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerContractImplUnitTest {

    @Mock
    UserEntityService userEntityService;

    @Mock
    RestaurantServiceClient restaurantServiceClient;

    @InjectMocks
    UserControllerContractImpl userControllerContractImpl;

    @Test
    void shouldGetById() {
        //given
        Long id = 3L;
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        //when
        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);

        UserDTO result = userControllerContractImpl.getById(id);

        //then
        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals(user.getName(), result.name());
        assertEquals(user.getSurname(), result.surname());
        assertEquals(user.getCountry(), result.country());
        assertEquals(user.getCity(), result.city());
        assertEquals(user.getDistrict(), result.district());
        assertEquals(user.getReviewCount(), result.reviewCount());

        InOrder inOrder = Mockito.inOrder(userEntityService);
        inOrder.verify(userEntityService).findByIdWithControl(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldNotGetByIdWhenUserStatusIsPassive() {
        //given
        Long id = 3L;

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        user.setStatus(EnumStatus.PASSIVE);

        //when
        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);

        //then
        AppBusinessException appBusinessException = assertThrows(AppBusinessException.class,
                () -> userControllerContractImpl.getById(id));
        assertEquals(UserErrorMessage.USER_IS_NOT_ACTIVE, appBusinessException.getBaseErrorMessage());
    }

    @Test
    void shouldGetAll() {
        //given
        UserFaker userFaker = new UserFaker();
        List<User> userList = userFaker.userList();

        //when
        Mockito.when(userEntityService.findAll()).thenReturn(userList);

        List<UserDTO> results = userControllerContractImpl.getAll();

        //then
        assertEquals(userList.size(), results.size());

        for(int i = 0; i < results.size(); i++) {
            UserDTO result = results.get(i);
            User user = userList.get(i);

            assertEquals(user.getId(), result.id());
            assertEquals(user.getName(), result.name());
            assertEquals(user.getSurname(), result.surname());
            assertEquals(user.getCountry(), result.country());
            assertEquals(user.getCity(), result.city());
            assertEquals(user.getDistrict(), result.district());
            assertEquals(user.getReviewCount(), result.reviewCount());
        }

        InOrder inOrder = Mockito.inOrder(userEntityService);
        inOrder.verify(userEntityService).findAll();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldNotGetAllWhenActiveUserListIsEmpty() {
        //given
        List<User> activeUserList = new ArrayList<>();

        //when
        Mockito.when(userEntityService.findAll()).thenReturn(activeUserList);

        //then
        AppBusinessException appBusinessException = assertThrows(AppBusinessException.class,
                () -> userControllerContractImpl.getAll());
        assertEquals(UserErrorMessage.NO_ACTIVE_USERS, appBusinessException.getBaseErrorMessage());
    }

    @Test
    void shouldSave() {
        //given
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        UserSaveRequestFaker userSaveRequestFaker = new UserSaveRequestFaker();
        UserSaveRequest userSaveRequest = userSaveRequestFaker.userSaveRequest();

        //when
        Mockito.when(userEntityService.getUserByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userEntityService.save(Mockito.any(User.class))).thenReturn(user);

        UserDTO result = userControllerContractImpl.save(userSaveRequest);

        //then
        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals(user.getName(), result.name());
        assertEquals(user.getSurname(), result.surname());
        assertEquals(user.getCountry(), result.country());
        assertEquals(user.getCity(), result.city());
        assertEquals(user.getDistrict(), result.district());
        assertEquals(user.getReviewCount(), result.reviewCount());
    }

    @Test
    void shouldNotSaveWhenUserWithSameEmailExist() {
        //given
        UserSaveRequestFaker userSaveRequestFaker = new UserSaveRequestFaker();
        UserSaveRequest userSaveRequest = userSaveRequestFaker.userSaveRequest();

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        //when
        Mockito.when(userEntityService.getUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        //then
        AppBusinessException appBusinessException = assertThrows(AppBusinessException.class,
                () -> userControllerContractImpl.save(userSaveRequest));
        assertEquals(UserErrorMessage.USER_ALREADY_EXISTS, appBusinessException.getBaseErrorMessage());
    }

    @Test
    void shouldUpdate() {
        //given
        Long id = 3L;

        UserUpdateRequestFaker userUpdateRequestFaker = new UserUpdateRequestFaker();
        UserUpdateRequest userUpdateRequest = userUpdateRequestFaker.userUpdateRequest();

        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        //when
        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);
        Mockito.when(userEntityService.save(Mockito.any(User.class))).thenReturn(user);

        UserDTO result = userControllerContractImpl.update(id, userUpdateRequest);

        //then
        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals(user.getName(), result.name());
        assertEquals(user.getSurname(), result.surname());
        assertEquals(user.getCountry(), result.country());
        assertEquals(user.getCity(), result.city());
        assertEquals(user.getDistrict(), result.district());
        assertEquals(user.getReviewCount(), result.reviewCount());

        InOrder inOrder = Mockito.inOrder(userEntityService);
        inOrder.verify(userEntityService).findByIdWithControl(id);
        inOrder.verify(userEntityService).save(user);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldDelete() {
        //given
        Long id = 3L;

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        //when
        Mockito.when(userEntityService.findByIdWithControl(id)).thenReturn(user);
        Mockito.doNothing().when(userEntityService).delete(user);

        userControllerContractImpl.delete(id);

        //then
        InOrder inOrder = Mockito.inOrder(userEntityService);
        inOrder.verify(userEntityService).findByIdWithControl(id);
        inOrder.verify(userEntityService).delete(user);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldActivate() {
        //given
        Long id = 3L;

        UserFaker userFaker = new UserFaker();
        User passiveUser = userFaker.user();
        passiveUser.setStatus(EnumStatus.PASSIVE);

        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        //when
        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(passiveUser);
        Mockito.when(userEntityService.save(Mockito.any(User.class))).thenReturn(passiveUser);

        UserDTO result = userControllerContractImpl.activate(id);

        //then
        assertNotNull(result);
        assertEquals(EnumStatus.ACTIVE, passiveUser.getStatus());

        InOrder inOrder = Mockito.inOrder(userEntityService);
        inOrder.verify(userEntityService).findByIdWithControl(id);
        inOrder.verify(userEntityService).save(passiveUser);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldNotActivateWhenUserIsAlreadyActive() {
        //given
        Long id = 3L;

        UserFaker userFaker = new UserFaker();
        User activeUser = userFaker.user();

        //when
        Mockito.when(userEntityService.findByIdWithControl(id)).thenReturn(activeUser);

        //then
        AppBusinessException appBusinessException = assertThrows(AppBusinessException.class,
                () -> userControllerContractImpl.activate(id));
        assertEquals(UserErrorMessage.USER_IS_ALREADY_ACTIVE, appBusinessException.getBaseErrorMessage());
    }

    @Test
    void shouldDeactivate() {
        //given
        Long id = 3L;

        UserFaker userFaker = new UserFaker();
        User activeUser = userFaker.user();

        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        //when
        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(activeUser);
        Mockito.when(userEntityService.save(Mockito.any(User.class))).thenReturn(activeUser);

        UserDTO result = userControllerContractImpl.deactivate(id);

        //then
        assertNotNull(result);
        assertEquals(EnumStatus.PASSIVE, activeUser.getStatus());

        InOrder inOrder = Mockito.inOrder(userEntityService);
        inOrder.verify(userEntityService).findByIdWithControl(id);
        inOrder.verify(userEntityService).save(activeUser);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldNotDeactivateWhenUserIsAlreadyActive() {
        //given
        Long id = 3L;

        UserFaker userFaker = new UserFaker();
        User passiveUser = userFaker.user();
        passiveUser.setStatus(EnumStatus.PASSIVE);

        //when
        Mockito.when(userEntityService.findByIdWithControl(id)).thenReturn(passiveUser);

        //then
        AppBusinessException appBusinessException = assertThrows(AppBusinessException.class,
                () -> userControllerContractImpl.deactivate(id));
        assertEquals(UserErrorMessage.USER_IS_ALREADY_PASSIVE, appBusinessException.getBaseErrorMessage());
    }

    @Test
    void shouldGetRecommendedRestaurants() {
        //given
        Long id = 3L;

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        RestaurantDTOFaker restaurantDTOFaker = new RestaurantDTOFaker();
        List<RestaurantDTO> restaurantDTOList = restaurantDTOFaker.restaurantDTOList();
        ResponseEntity<RestResponse<List<RestaurantDTO>>> restaurantResponse = new ResponseEntity<>(RestResponse.of(restaurantDTOList), HttpStatus.OK);

        //when
        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);
        Mockito.when(restaurantServiceClient.getAllRestaurants()).thenReturn(restaurantResponse);

        List<RestaurantDTO> results = userControllerContractImpl.getRecommendedRestaurants(id);
        //then
        assertEquals(3, results.size());

        InOrder inOrder = Mockito.inOrder(userEntityService);
        inOrder.verify(userEntityService).findByIdWithControl(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldGetRecommendedRestaurantsWhenRestaurantListSizeLowerThan3() {
        //given
        Long id = 3L;

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        RestaurantDTOFaker restaurantDTOFaker = new RestaurantDTOFaker();
        List<RestaurantDTO> restaurantDTOs = restaurantDTOFaker.restaurantDTOList();
        List<RestaurantDTO> restaurantDTOList = restaurantDTOs.subList(0,2);
        ResponseEntity<RestResponse<List<RestaurantDTO>>> restaurantResponse = new ResponseEntity<>(RestResponse.of(restaurantDTOList), HttpStatus.OK);

        //when
        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);
        Mockito.when(restaurantServiceClient.getAllRestaurants()).thenReturn(restaurantResponse);

        List<RestaurantDTO> results = userControllerContractImpl.getRecommendedRestaurants(id);
        //then
        assertEquals(restaurantDTOList.size(), results.size());

        InOrder inOrder = Mockito.inOrder(userEntityService);
        inOrder.verify(userEntityService).findByIdWithControl(id);
        inOrder.verifyNoMoreInteractions();
    }
}