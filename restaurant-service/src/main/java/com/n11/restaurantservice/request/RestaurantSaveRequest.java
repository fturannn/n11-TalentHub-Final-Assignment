package com.n11.restaurantservice.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

public record RestaurantSaveRequest(@NotBlank @Size(min = 1, max = 100) String name,
                                    @Size(min = 11, max = 11) String phoneNumber,
                                    @Email @NotBlank String email,
                                    @NotBlank String country,
                                    @NotBlank String city,
                                    @NotBlank String district,
                                    @NotNull Double latitude,
                                    @NotNull Double longitude,
                                    @NotNull LocalTime openingHour,
                                    @NotNull LocalTime closingHour,
                                    @Size(max = 500) String description) {
}
