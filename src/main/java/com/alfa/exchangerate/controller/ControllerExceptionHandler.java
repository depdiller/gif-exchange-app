package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.model.FeignExceptionObject;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    ResponseEntity<Object> exceptionHandler(ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> exceptionHandler(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignStatusException(FeignException e) {
        FeignExceptionObject exceptionObject = new FeignExceptionObject(e.contentUTF8(), e.getMessage(), HttpStatus.valueOf(e.status()));
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
}
