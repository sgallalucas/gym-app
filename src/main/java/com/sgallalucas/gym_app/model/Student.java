package com.sgallalucas.gym_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "student")
@EntityListeners(AuditingEntityListener.class)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private String genre;
    @CreatedDate
    private Instant creationDate;
    @LastModifiedDate
    private Instant updateDate;

    public Student() {
    }

    public Student(UUID id, String name, String email, LocalDate birthDate, String genre) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.genre = genre;
    }
}
