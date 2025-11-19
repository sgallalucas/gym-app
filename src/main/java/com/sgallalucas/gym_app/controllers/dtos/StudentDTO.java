package com.sgallalucas.gym_app.controllers.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record StudentDTO (
        UUID id,
        @NotBlank(message = "required field")
        @Size(max = 150, min = 2, message = "invalid size")
        String name,
        @Email(message = "invalid email")
        @NotBlank(message = "required field")
        String email,
        @NotNull(message = "required field")
        @Past(message = "birth date must be a past date")
        LocalDate birthDate,
        @NotBlank(message = "required field")
        String genre
) {
}
