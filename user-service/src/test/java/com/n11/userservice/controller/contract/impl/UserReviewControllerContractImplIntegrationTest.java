package com.n11.userservice.controller.contract.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n11.userservice.BaseTest;
import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.client.RestaurantServiceClient;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.faker.UserReviewFaker;
import com.n11.userservice.faker.UserReviewUpdateRequestFaker;
import com.n11.userservice.request.UserReviewUpdateRequest;
import com.n11.userservice.service.UserEntityService;
import com.n11.userservice.service.UserReviewEntityService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserServiceApplication.class})
class UserReviewControllerContractImplIntegrationTest extends BaseTest {

    @MockBean
    private UserReviewEntityService userReviewEntityService;

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

    @Autowired
    private WebApplicationContext context;

    @Test
    void shouldGetById() throws Exception{
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        UserReview userReview = userReviewFaker.userReview();

        Mockito.when(userReviewEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(userReview);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetAll() throws Exception{
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        List<UserReview> userReviewList = userReviewFaker.userReviewList();

        Mockito.when(userReviewEntityService.findAll()).thenReturn(userReviewList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetAllWhenNoReviewsAvailable() throws Exception{
        List<UserReview> userReviewList = new ArrayList<>();

        Mockito.when(userReviewEntityService.findAll()).thenReturn(userReviewList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldSave() throws Exception{

        String requestAsString = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"restaurantId\": \"1ba2bd30-4387-4f91-b082-7664194d457d\",\n" +
                "    \"text\": \"Test review!\",\n" +
                "    \"score\": \"FIVE\"\n" +
                "}";

        UserReviewFaker userReviewFaker = new UserReviewFaker();
        UserReview userReview = userReviewFaker.userReview();

        Mockito.when(userEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(new User());
        Mockito.when(userReviewEntityService.save(Mockito.any(UserReview.class))).thenReturn(userReview);
        Mockito.doNothing().when(restaurantServiceClient).updateRestaurantScoreAccordingToUserReview(Mockito.anyString(), Mockito.anyInt());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/reviews")
                        .content(requestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldUpdate() throws Exception{
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        UserReview userReview = userReviewFaker.userReview();

        UserReviewUpdateRequestFaker userReviewUpdateRequestFaker = new UserReviewUpdateRequestFaker();
        UserReviewUpdateRequest userReviewUpdateRequest = userReviewUpdateRequestFaker.userReviewUpdateRequest();

        Mockito.when(userReviewEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(userReview);
        Mockito.when(userReviewEntityService.save(Mockito.any(UserReview.class))).thenReturn(userReview);

        String requestAsString = "{\n" +
                "    \"id\": 1,\n" +
                "    \"text\": \"new text\",\n" +
                "    \"score\": \"FOUR\"\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reviews/{id}", userReview.getId())
                        .content(requestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldDelete() throws Exception{
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        UserReview userReview = userReviewFaker.userReview();

        Mockito.when(userReviewEntityService.findByIdWithControl(Mockito.anyLong())).thenReturn(userReview);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/reviews/{id}", userReview.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetReviewsByUserName() throws Exception {
        String name = "test name";
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        List<UserReview> userReviewList = userReviewFaker.userReviewList();

        Mockito.when(userReviewEntityService.findReviewsByUserName(Mockito.anyString())).thenReturn(userReviewList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/with-user-name?name={name}", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotGetReviewsByUserNameIfUserNotExists() throws Exception {
        String name = "test name";

        List<UserReview> userReviewList = new ArrayList<>();

        Mockito.when(userReviewEntityService.findReviewsByUserName(Mockito.anyString())).thenReturn(userReviewList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/with-user-name?name={name}", name))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetReviewsByUserId() throws Exception{
        Long id = 1L;
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        List<UserReview> userReviewList = userReviewFaker.userReviewList();

        Mockito.when(userReviewEntityService.findReviewsByUserId(Mockito.anyLong())).thenReturn(userReviewList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/with-user-id/{userId}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotGetReviewsByUserIdWhenUserNotExists() throws Exception{
        Long id = 1L;

        List<UserReview> userReviewList = new ArrayList<>();

        Mockito.when(userReviewEntityService.findReviewsByUserId(Mockito.anyLong())).thenReturn(userReviewList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/with-user-id/{userId}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetReviewsByRestaurantId() throws Exception{
        String id = "7c7ac2ee-725b-450d-b379-b180ee003a5d";
        UserReviewFaker userReviewFaker = new UserReviewFaker();
        List<UserReview> userReviewList = userReviewFaker.userReviewList();

        Mockito.when(userReviewEntityService.findReviewsByRestaurantId(Mockito.anyString())).thenReturn(userReviewList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/with-restaurant-id/{userId}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotGetReviewsByRestaurantIdWhenAnyReviewExists() throws Exception{
        String id = "7c7ac2ee-725b-450d-b379-b180ee003a5d";
        List<UserReview> userReviewList = new ArrayList<>();

        Mockito.when(userReviewEntityService.findReviewsByRestaurantId(Mockito.anyString())).thenReturn(userReviewList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/with-restaurant-id/{userId}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}