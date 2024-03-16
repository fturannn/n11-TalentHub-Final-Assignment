package com.n11.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n11.userservice.BaseTest;
import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.faker.UserReviewDTOFaker;
import com.n11.userservice.faker.UserReviewFaker;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;
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
class UserReviewControllerIntegrationTest extends BaseTest {

    @MockBean
    private UserReviewControllerContract userReviewControllerContract;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Autowired
    private WebApplicationContext context;

    @Test
    void shouldGetAllReviews() throws Exception{
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        List<UserReviewDTO> userReviewDTOList = userReviewDTOFaker.userReviewDTOList();

        Mockito.when(userReviewControllerContract.getAll()).thenReturn(userReviewDTOList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetReviewById() throws Exception{
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        UserReviewDTO userReviewDTO = userReviewDTOFaker.userReviewDTO();

        Mockito.when(userReviewControllerContract.getById(Mockito.anyLong())).thenReturn(userReviewDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldSaveReview() throws Exception{
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        UserReviewDTO userReviewDTO = userReviewDTOFaker.userReviewDTO();

        String requestAsString = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"restaurantId\": \"1ba2bd30-4387-4f91-b082-7664194d457d\",\n" +
                "    \"text\": \"Test review!\",\n" +
                "    \"score\": \"FIVE\"\n" +
                "}";

        Mockito.when(userReviewControllerContract.save(Mockito.any(UserReviewSaveRequest.class))).thenReturn(userReviewDTO);

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
    void shouldUpdateReview() throws Exception{
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        UserReviewDTO userReviewDTO = userReviewDTOFaker.userReviewDTO();

        String requestAsString = "{\n" +
                "    \"id\": 1,\n" +
                "    \"text\": \"new text\",\n" +
                "    \"score\": \"FOUR\"\n" +
                "}";

        Mockito.when(userReviewControllerContract.update(Mockito.anyLong(), Mockito.any(UserReviewUpdateRequest.class))).thenReturn(userReviewDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reviews/{id}", userReviewDTO.id())
                        .content(requestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldDeleteReview() throws Exception{
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        UserReviewDTO userReviewDTO = userReviewDTOFaker.userReviewDTO();

        Mockito.doNothing().when(userReviewControllerContract).delete(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/reviews/{id}", userReviewDTO.id()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetReviewsByUserName() throws Exception{
        String name = "test name";
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        List<UserReviewDTO> userReviewDTOList = userReviewDTOFaker.userReviewDTOList();

        Mockito.when(userReviewControllerContract.getReviewsByUserName(Mockito.anyString())).thenReturn(userReviewDTOList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/with-user-name?name={name}", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetReviewsByUserId() throws Exception{
        Long id = 1L;
        UserReviewDTOFaker userReviewDTOFaker = new UserReviewDTOFaker();
        List<UserReviewDTO> userReviewDTOList = userReviewDTOFaker.userReviewDTOList();

        Mockito.when(userReviewControllerContract.getReviewsByUserId(Mockito.anyLong())).thenReturn(userReviewDTOList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/reviews/with-user-id/{userId}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }
}