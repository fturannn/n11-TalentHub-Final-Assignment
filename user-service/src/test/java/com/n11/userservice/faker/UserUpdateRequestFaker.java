package com.n11.userservice.faker;

import com.n11.userservice.enums.EnumGender;
import com.n11.userservice.enums.EnumStatus;
import com.n11.userservice.request.UserUpdateRequest;

import java.time.LocalDate;

public class UserUpdateRequestFaker {

    public UserUpdateRequest userUpdateRequest() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(1L, "Test name", "Test surname"
                , "Test country", "Test city", "Test district", 36
                , 42, LocalDate.of(1998,11,8), "test@email.com"
                , "05055055050", EnumGender.MALE, "http://testUrl.com", EnumStatus.ACTIVE);
        return userUpdateRequest;
    }
}
