package com.n11.restaurantservice.entity;

import com.n11.restaurantservice.enums.EnumCuisineType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RESTAURANTS")
public class Restaurant {
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

    @Column(name = "OPENING_HOUR", nullable = false)
    private LocalTime openingHour;

    @Column(name = "CLOSING_TIME", nullable = false)
    private LocalTime closingHour;

    @Enumerated(EnumType.STRING)
    @Column(name = "CUISINE_TYPE", length = 30, nullable = false)
    private EnumCuisineType cuisineType;

    @ManyToMany
    @JoinTable(
            name = "FEATURES",
            joinColumns = @JoinColumn(name = "RESTAURANT_ID"),
            inverseJoinColumns = @JoinColumn(name = "FEATURE_ID"))
    private Set<Feature> features;

    @Column(name = "AVERAGE_RATING", nullable = false)
    private double averageRating;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

}
