package com.n11.userservice.entity;

import com.n11.userservice.enums.EnumScore;
import com.n11.userservice.general.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REVIEWS")
public class UserReview extends BaseEntity {
    @SequenceGenerator(name = "UserReview", sequenceName = "USER_REVIEW_ID_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserReview")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "RESTAURANT_ID", nullable = false)
    private String restaurantId;

    @Column(name = "TEXT", length = 500)
    private String text;

    @Column(name = "REVIEW_DATE", nullable = false)
    private LocalDateTime reviewDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "SCORE", nullable = false)
    private EnumScore score;
}