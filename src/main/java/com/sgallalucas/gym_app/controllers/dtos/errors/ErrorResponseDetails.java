package com.sgallalucas.gym_app.controllers.dtos.errors;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDetails(
        Integer status,
        String message,
        LocalDateTime timestamp,
        List<FieldErrorDetails> errorList
) {
}
