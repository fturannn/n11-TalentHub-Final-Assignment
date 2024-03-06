package com.n11.restaurantservice.general;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class BaseAdditionalFields {
    private LocalDateTime dataCreateDate;
    private LocalDateTime dataUpdateDate;
}
