package com.pedro.health.infra.exceptions.global;

import com.pedro.health.infra.exceptions.document.DocumentNotFoundException;
import com.pedro.health.infra.exceptions.person.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorMessage> handlePersonNotFoundException(PersonNotFoundException ex){
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleDocumentNotFoundException(DocumentNotFoundException ex){
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.CONFLICT, Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }



}
