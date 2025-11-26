package com.sgallalucas.gym_app.model;

import com.sgallalucas.gym_app.model.enums.Specialty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "professor")
@EntityListeners(AuditingEntityListener.class)
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    @Column(unique = true)
    private String email;
    @NotNull
    private LocalDate birthDate;
    @NotBlank
    private String genre;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    @CreatedDate
    private Instant creationDate;
    @LastModifiedDate
    private Instant updateDate;
    @OneToMany(mappedBy = "professor")
    private List<Workout> workoutList;

    public Professor() {
    }

    public Professor(UUID id, String name, String email, LocalDate birthDate, String genre, Specialty specialty) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.genre = genre;
        this.specialty = specialty;
    }
}
