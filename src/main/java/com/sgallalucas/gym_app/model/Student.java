package com.sgallalucas.gym_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private String genre;

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
