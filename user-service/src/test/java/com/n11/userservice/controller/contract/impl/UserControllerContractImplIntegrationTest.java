package com.n11.userservice.controller.contract.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n11.userservice.BaseTest;
import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.client.RestaurantServiceClient;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumStatus;
import com.n11.userservice.faker.RestaurantDTOFaker;
import com.n11.userservice.faker.UserFaker;
import com.n11.userservice.faker.UserUpdateRequestFaker;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserUpdateRequest;
import com.n11.userservice.service.UserEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserServiceApplication.class})
class UserControllerContractImplIntegrationTest extends BaseTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserEntityService userEntityService;

    @MockBean
    private RestaurantServiceClient restaurantServiceClient;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldGetById() throws Exception{
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotGetByIdWhenUserIsPassive() throws Exception{
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();
        user.setStatus(EnumStatus.PASSIVE);

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetAll() throws Exception{
        UserFaker userFaker = new UserFaker();
        List<User> userList = userFaker.userList();

        Mockito.when(userEntityService.findAll()).thenReturn(userList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotGetAllWhenAnyActiveUserExists() throws Exception{
        List<User> userList = new ArrayList<>();

        Mockito.when(userEntityService.findAll()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldSave() throws Exception{
        String requestAsString = "{\n" +
                "    \"name\": \"Test Name\",\n" +
                "    \"surname\": \"Test Surname\",\n" +
                "    \"country\": \"Türkiye\",\n" +
                "    \"city\": \"İstanbul\",\n" +
                "    \"district\": \"Sancaktepe\",\n" +
                "    \"latitude\": 40.9179,\n" +
                "    \"longitude\": 35.3925,\n" +
                "    \"birthDate\": \"1998-11-08\",\n" +
                "    \"email\": \"test@gmail.com\",\n" +
                "    \"phoneNumber\": \"05055055050\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"photoUrl\": \"http://testUrl.com\"\n" +
                "}";

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        Mockito.when(userEntityService.save(Mockito.any(User.class))).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(requestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotSaveWhenUserExists() throws Exception{
        String requestAsString = "{\n" +
                "    \"name\": \"Test Name\",\n" +
                "    \"surname\": \"Test Surname\",\n" +
                "    \"country\": \"Türkiye\",\n" +
                "    \"city\": \"İstanbul\",\n" +
                "    \"district\": \"Sancaktepe\",\n" +
                "    \"latitude\": 40.9179,\n" +
                "    \"longitude\": 35.3925,\n" +
                "    \"birthDate\": \"1998-11-08\",\n" +
                "    \"email\": \"test@gmail.com\",\n" +
                "    \"phoneNumber\": \"05055055050\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"photoUrl\": \"http://testUrl.com\"\n" +
                "}";

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();
        user.setEmail("test@gmail.com");

        Mockito.when(userEntityService.getUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(requestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldUpdate() throws Exception{

        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        UserUpdateRequestFaker userUpdateRequestFaker = new UserUpdateRequestFaker();
        UserUpdateRequest userUpdateRequest = userUpdateRequestFaker.userUpdateRequest();

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);
        Mockito.when(userEntityService.save(Mockito.any(User.class))).thenReturn(user);

        String updateRequestBody = objectMapper.writeValueAsString(userUpdateRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{id}", 1)
                        .content(updateRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
}

    @Test
    void shouldDelete() throws Exception{
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldActivate() throws Exception{
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();
        user.setStatus(EnumStatus.PASSIVE);

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/{id}/activate", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotActivateWhenUserIsAlreadyActive() throws Exception{
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/{id}/activate", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldDeactivate() throws Exception{
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/{id}/deactivate", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotDeactivateWhenUserIsAlreadyPassive() throws Exception{
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();
        user.setStatus(EnumStatus.PASSIVE);

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/{id}/deactivate", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetRecommendedRestaurants() throws Exception{
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        RestaurantDTOFaker restaurantDTOFaker = new RestaurantDTOFaker();
        List<RestaurantDTO> restaurantDTOList = restaurantDTOFaker.restaurantDTOList();

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);
        Mockito.when(restaurantServiceClient.getAllRestaurants()).thenReturn(ResponseEntity.ok(RestResponse.of(restaurantDTOList)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/recommended-restaurants/{id}", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetRecommendedRestaurantsWhenRestaurantListSizeLowerThan3() throws Exception{
        UserFaker userFaker = new UserFaker();
        User user = userFaker.user();

        RestaurantDTOFaker restaurantDTOFaker = new RestaurantDTOFaker();
        List<RestaurantDTO> restaurantDTOList = restaurantDTOFaker.restaurantDTOList();
        restaurantDTOList.remove(2);
        restaurantDTOList.remove(2);
        restaurantDTOList.remove(2);

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(user);
        Mockito.when(restaurantServiceClient.getAllRestaurants()).thenReturn(ResponseEntity.ok(RestResponse.of(restaurantDTOList)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/recommended-restaurants/{id}", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }
}