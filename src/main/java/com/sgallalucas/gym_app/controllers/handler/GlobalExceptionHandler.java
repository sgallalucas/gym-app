package com.sgallalucas.gym_app.controllers.handler;

import com.sgallalucas.gym_app.controllers.dtos.errors.ErrorResponseDetails;
import com.sgallalucas.gym_app.controllers.dtos.errors.FieldErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseDetails handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldError = ex.getFieldErrors();

        List<FieldErrorDetails> details = fieldError.stream()
                .map((fe) -> new FieldErrorDetails(fe.getField(), fe.getDefaultMessage())).toList();

        return new ErrorResponseDetails(HttpStatus.UNPROCESSABLE_ENTITY.value(), "validation error", LocalDateTime.now(), details);
    }
}
