package com.n11.restaurantservice.entity;

import com.n11.restaurantservice.enums.EnumCuisineType;
import com.n11.restaurantservice.enums.EnumFeatureType;
import com.n11.restaurantservice.general.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RESTAURANTS")
public class Restaurant extends BaseEntity {
    @SequenceGenerator(name = "Restaurant", sequenceName = "RESTAURANT_ID_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Restaurant")
    private Long id;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "PHONE_NUMBER", length = 11, nullable = false)
    private String phoneNumber;

    @Email
    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "COUNTRY", length = 100, nullable = false)
    private String country;

    @Column(name = "CITY", length = 100, nullable = false)
    private String city;

    @Column(name = "DISTRICT", length = 100, nullable = false)
    private String district;

    @Column(name = "LATITUDE", nullable = false)
    private double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private double longitude;

    @Column(name = "OPENING_HOUR", nullable = false)
    private LocalTime openingHour;

    @Column(name = "CLOSING_TIME", nullable = false)
    private LocalTime closingHour;

    @Enumerated(EnumType.STRING)
    @Column(name = "CUISINE_TYPE", length = 30, nullable = false)
    private EnumCuisineType cuisineType;

    @Column(name = "FEATURES")
    private List<EnumFeatureType> features;

    @Column(name = "TOTAL_REVIEW_NUMBER")
    private long totalReviewNumber;

    @Column(name = "AVERAGE_RATING")
    private double averageRating;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

}
