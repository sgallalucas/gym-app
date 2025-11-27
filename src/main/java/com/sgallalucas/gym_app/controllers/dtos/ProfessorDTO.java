package com.sgallalucas.gym_app.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sgallalucas.gym_app.model.enums.Specialty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record ProfessorDTO(
        UUID id,
        @NotBlank(message = "required field")
        @Size(max = 150, min = 2, message = "invalid size")
        String name,
        @NotBlank(message = "required field")
        @Email(message = "invalid email")
        String email,
        @NotNull(message = "required field")
        @Past(message = "birth date must be a past date")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate birthDate,
        @NotBlank(message = "required field")
        String genre,
        @NotNull(message = "required field")
        Specialty specialty
) {
}
