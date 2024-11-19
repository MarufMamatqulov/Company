package com.example.Company.Config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        // Xatolik xabarini moslashtirish
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Sizning lavozimingiz bu amalni bajarishga ruxsat bermaydi.");
    }
}


