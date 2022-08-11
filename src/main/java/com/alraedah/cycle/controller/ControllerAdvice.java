package com.alraedah.cycle.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().atZone(UTC).format(ISO_ZONED_DATE_TIME));
        body.put("status", status.value());

        Map<String, Object> fieldError = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldError.put(error.getField(), error.getDefaultMessage());
        });

        body.put("fields", fieldError);
        return new ResponseEntity<>(body, BAD_REQUEST);
    }
}
