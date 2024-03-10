package com.n11.userservice.faker;

import com.n11.userservice.enums.EnumGender;
import com.n11.userservice.request.UserSaveRequest;

import java.time.LocalDate;

public class UserSaveRequestFaker {

    public UserSaveRequest userSaveRequest() {
        UserSaveRequest userSaveRequest = new UserSaveRequest("Test name", "Test surname"
                , "Test country", "Test city", "Test district", 23
                , 23, LocalDate.of(1998,11,8), "test@email.com"
                , "05055055050", EnumGender.MALE, "testphoto.png");
        return userSaveRequest;
    }
}
