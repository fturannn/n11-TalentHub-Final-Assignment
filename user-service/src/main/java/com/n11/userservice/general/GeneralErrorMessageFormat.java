package com.n11.userservice.general;

import java.time.LocalDateTime;

public record GeneralErrorMessageFormat(LocalDateTime date, String message, String description) {
}
