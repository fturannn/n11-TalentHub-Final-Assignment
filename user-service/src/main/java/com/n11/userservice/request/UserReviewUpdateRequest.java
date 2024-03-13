package com.n11.userservice.request;

import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumScore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record UserReviewUpdateRequest(@NotNull Long id,
                                      @NotBlank @Length(min = 0, max = 500) String text,
                                      @NotNull EnumScore score) {
}
