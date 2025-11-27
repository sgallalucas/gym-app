package com.sgallalucas.gym_app.controllers.dtos;

import com.sgallalucas.gym_app.model.enums.WorkoutType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record WorkoutDTO(
        UUID id,
        @NotBlank(message = "required field")
        String name,
        @NotBlank(message = "required field")
        String description,
        @NotNull
        WorkoutType type,
        @NotNull(message = "required field")
        String student,
        @NotNull(message = "required field")
        String professor
) {
}
