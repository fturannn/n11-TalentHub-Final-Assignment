package com.n11.userservice.request;

import com.n11.userservice.enums.EnumGender;
import com.n11.userservice.enums.EnumStatus;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

public record UserUpdateRequest(@NotNull Long id,
                                @NotBlank @Length(min = 3, max = 100) String name,
                                @NotBlank @Length(min = 3, max = 100) String surname,
                                @NotBlank @Length(min = 3, max = 100) String country,
                                @NotBlank @Length(min = 3, max = 100) String city,
                                @NotBlank @Length(min = 3, max = 100) String district,
                                @NotNull @Min(-90) @Max(90) double latitude,
                                @NotNull @Min(-180) @Max(180) double longitude,
                                @NotNull LocalDate birthDate,
                                @Email @NotBlank @Max(100) String email,
                                @Length(max = 11, min = 11) String phoneNumber,
                                @NotNull EnumGender gender,
                                @URL String photoUrl,
                                EnumStatus status) {
}
