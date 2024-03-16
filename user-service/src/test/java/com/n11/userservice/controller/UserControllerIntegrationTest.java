package com.n11.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n11.userservice.BaseTest;
import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.faker.RestaurantDTOFaker;
import com.n11.userservice.faker.UserDTOFaker;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserServiceApplication.class})
class UserControllerIntegrationTest extends BaseTest {

    @MockBean
    private UserControllerContract userControllerContract;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }


    @Test
    void shouldGetAllUsers() throws Exception{
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        List<UserDTO> userDTOList = userDTOFaker.userDTOList();

        Mockito.when(userControllerContract.getAll()).thenReturn(userDTOList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetUserById() throws Exception{
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        Mockito.when(userControllerContract.getById(Mockito.anyLong())).thenReturn(userDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}", userDTO.id()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldSaveUser() throws Exception{
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

        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        Mockito.when(userControllerContract.save(Mockito.any(UserSaveRequest.class))).thenReturn(userDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(requestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldUpdateUser() throws Exception{
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        String requestAsString = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Test name\",\n" +
                "    \"surname\": \"Test surname\",\n" +
                "    \"country\": \"Türkiye\",\n" +
                "    \"city\": \"İstanbul\",\n" +
                "    \"district\": \"Beşiktaş\",\n" +
                "    \"latitude\": 36,\n" +
                "    \"longitude\": 42,\n" +
                "    \"birthDate\": \"1998-11-08\",\n" +
                "    \"email\": \"testemail@gmail.com\",\n" +
                "    \"phoneNumber\": \"05055055050\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"photoUrl\": \"http://testUrl.com\",\n" +
                "    \"status\": \"PASSIVE\"\n" +
                "}";

        Mockito.when(userControllerContract.update(Mockito.anyLong(), Mockito.any(UserUpdateRequest.class))).thenReturn(userDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{id}", userDTO.id())
                        .content(requestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldDeleteUser() throws Exception{
        Mockito.doNothing().when(userControllerContract).delete(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldActivateUser() throws Exception{
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        Mockito.when(userControllerContract.activate(Mockito.anyLong())).thenReturn(userDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/{id}/activate", userDTO.id()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldDeactivateUser() throws Exception{
        UserDTOFaker userDTOFaker = new UserDTOFaker();
        UserDTO userDTO = userDTOFaker.userDTO();

        Mockito.when(userControllerContract.deactivate(Mockito.anyLong())).thenReturn(userDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/{id}/deactivate", userDTO.id()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetRecommendedRestaurants() throws Exception{
        Long id = 1L;
        RestaurantDTOFaker restaurantDTOFaker = new RestaurantDTOFaker();
        List<RestaurantDTO> restaurantDTOList = restaurantDTOFaker.restaurantDTOList();

        Mockito.when(userControllerContract.getRecommendedRestaurants(Mockito.anyLong())).thenReturn(restaurantDTOList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/recommended-restaurants/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }
}