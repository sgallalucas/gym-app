package com.sgallalucas.gym_app.controllers.dtos;

import com.sgallalucas.gym_app.model.Professor;
import com.sgallalucas.gym_app.model.Student;

import java.util.UUID;

public record WorkoutDTO(
        UUID id,
        String name,
        String description,
        Student student,
        Professor professor
) {
}
