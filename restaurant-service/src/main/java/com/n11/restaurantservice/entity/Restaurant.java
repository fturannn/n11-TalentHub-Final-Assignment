package com.n11.restaurantservice.entity;

import com.n11.restaurantservice.general.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SolrDocument(solrCoreName = "restaurants")
public class Restaurant extends BaseEntity {
    @Id
    @Indexed(name = "id", type = "string")
    private String id;

    @Indexed(name = "name", type = "string")
    private String name;

    @Indexed(name = "phone_number", type = "string")
    private String phoneNumber;

    @Indexed(name = "email", type = "string")
    private String email;

    @Indexed(name = "country", type = "string")
    private String country;

    @Indexed(name = "city", type = "string")
    private String city;

    @Indexed(name = "district", type = "string")
    private String district;

    @Indexed(name = "latitude", type = "pdouble")
    private Double latitude;

    @Indexed(name = "longitude", type = "pdouble")
    private Double longitude;

    @Indexed(name = "opening_hour", type = "ptime")
    private LocalTime openingHour;

    @Indexed(name = "closing_hour", type = "ptime")
    private LocalTime closingHour;

    @Indexed(name = "total_review_number", type = "plong")
    private Long totalReviewNumber;

    @Indexed(name = "average_rating", type = "pdouble")
    private Double averageRating;

    @Indexed(name = "description", type = "string")
    private String description;
}
