package com.n11.userservice.faker;

import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.enums.EnumScore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserReviewDTOFaker {

    public UserReviewDTO userReviewDTO() {
        UserReviewDTO userReviewDTO = new UserReviewDTO(1L, "Test Name", "Test Surname",
                "Test FullName", "59bd8e58-7cd9-44b6-9a6e-040aca58f639", "Test Review Text", LocalDateTime.now(), EnumScore.FOUR);
        return userReviewDTO;
    }

    public List<UserReviewDTO> userReviewDTOList() {
        UserReviewDTO userReviewDTO1 = new UserReviewDTO(1L, "Test Name 1", "Test Surname 1",
                "Test FullName 1", "59bd8e58-7cd9-44b6-9a6e-040aca58f639", "Test Review Text 1", LocalDateTime.now(), EnumScore.FOUR);
        UserReviewDTO userReviewDTO2 = new UserReviewDTO(2L, "Test Name 2", "Test Surname 2",
                "Test FullName 2", "59bd8e58-7cd9-44b6-9a6e-040aca58f638", "Test Review Text 2", LocalDateTime.now(), EnumScore.FOUR);

        List<UserReviewDTO> userReviewDTOList = new ArrayList<>();
        userReviewDTOList.add(userReviewDTO1);
        userReviewDTOList.add(userReviewDTO2);

        return userReviewDTOList;
    }
}
