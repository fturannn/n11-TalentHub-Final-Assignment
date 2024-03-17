package com.n11.logger.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Log {

    @Id
    @GeneratedValue(generator = "Log", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "Log", sequenceName = "LOG_ID_SEQ")
    private Long id;

    private LocalDateTime date;
    private String message;
    private String description;
}
