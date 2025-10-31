package com.sgallalucas.gym_app.model.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record StudentDTO (
        UUID id,
        String name,
        String email,
        LocalDate birthDate,
        String genre
) {
}
