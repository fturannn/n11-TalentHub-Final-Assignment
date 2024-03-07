package com.n11.userservice.request;

import com.n11.userservice.enums.EnumGender;
import com.n11.userservice.enums.EnumStatus;

import java.time.LocalDate;

public record UserSaveRequest(String name,
                              String surname,
                              String country,
                              String city,
                              String district,
                              double latitude,
                              double longitude,
                              LocalDate birthDate,
                              String email,
                              String phoneNumber,
                              EnumGender gender,
                              String photoUrl) {
}
