package com.sgallalucas.gym_app.controllers.handler;

import com.sgallalucas.gym_app.controllers.dtos.errors.ErrorResponseDetails;
import com.sgallalucas.gym_app.controllers.dtos.errors.FieldErrorDetails;
import com.sgallalucas.gym_app.exceptions.DuplicateRecordException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDetails handleDuplicateRecordException(DuplicateRecordException ex) {
        return new ErrorResponseDetails(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now(), List.of());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDetails handleDateTimeParseException(DateTimeParseException ex) {
        return new ErrorResponseDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now(), List.of());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDetails handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ErrorResponseDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now(), List.of());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDetails handleUnexpectedError(RuntimeException ex) {
        return new ErrorResponseDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error", LocalDateTime.now(), List.of());
    }
}
