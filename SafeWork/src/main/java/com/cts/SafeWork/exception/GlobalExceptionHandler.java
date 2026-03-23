package com.cts.SafeWork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Employee aur Document dono ke "Not Found" cases handle karega
    @ExceptionHandler({EmployeeNotFoundException.class, DocumentNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(RuntimeException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", new Date());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("details", request.getDescription(false));
        errorDetails.put("status", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // 2. Generic Exception (Kuch bhi aur phata toh ye pakdega)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", new Date());
        errorDetails.put("message", "Internal Server Error: " + ex.getMessage()); // Debugging ke liye message rakha hai
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}