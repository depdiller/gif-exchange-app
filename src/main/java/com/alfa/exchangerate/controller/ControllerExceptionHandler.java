package com.alfa.exchangerate.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    String exceptionHandler(ValidationException e) {
        log.error(e.getMessage());
        return "error";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    String exceptionHandler(ConstraintViolationException e) {
        log.error(e.getMessage());
        return "error";
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignStatusException(FeignException e) {
        log.error(e.getMessage());
        log.info(e.contentUTF8());
        return "error";
    }
}
