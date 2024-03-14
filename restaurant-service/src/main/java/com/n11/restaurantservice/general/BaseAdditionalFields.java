package com.n11.restaurantservice.general;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.solr.core.mapping.Indexed;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class BaseAdditionalFields {
    @Indexed(name = "data_create_date", type = "pdate")
    private LocalDateTime dataCreateDate;
    @Indexed(name = "data_update_date", type = "pdate")
    private LocalDateTime dataUpdateDate;
}
