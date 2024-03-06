package com.n11.restaurantservice.entity;

import com.n11.restaurantservice.enums.EnumFeatureType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "FEATURES")
public class Feature {
    @SequenceGenerator(name = "Feature", sequenceName = "FEATURE_ID_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Feature")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "FEATURE_TYPE", nullable = false, length = 100)
    private EnumFeatureType featureType;

    @ManyToMany
    @Column(name = "RESTAURANTS")
    private Set<Restaurant> restaurants;
}
