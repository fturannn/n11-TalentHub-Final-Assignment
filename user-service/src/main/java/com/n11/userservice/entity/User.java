package com.n11.userservice.entity;

import com.n11.userservice.enums.EnumGender;
import com.n11.userservice.enums.EnumStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {
    @SequenceGenerator(name = "User", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "SURNAME", nullable = false, length = 100)
    private String surname;

    @Column(name = "COUNTRY", nullable = false, length = 100)
    private String country;

    @Column(name = "CITY", nullable = false, length = 100)
    private String city;

    @Column(name = "DISTRICT", nullable = false, length = 100)
    private String district;

    @Column(name = "LATITUDE", nullable = false)
    private double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private double longitude;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Email
    @Column(name = "EMAIL", nullable = false, length =  100)
    private String email;

    @Column(name = "PHONE_NUMBER", nullable = false, length = 11)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", length = 30)
    private EnumGender gender;

    @Column(name = "PHOTO_URL", length = 200)
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    private EnumStatus status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<UserReview> userReviews;
}
