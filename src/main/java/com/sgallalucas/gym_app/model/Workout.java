package com.sgallalucas.gym_app.model;

import com.sgallalucas.gym_app.model.enums.WorkoutType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "workout")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private String name;
    @NotNull
    private WorkoutType type;
    @NotBlank
    private String description;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    public Workout() {
    }

    public Workout(UUID id, String name, WorkoutType type, String description, Student student, Professor professor) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.student = student;
        this.professor = professor;
    }
}
