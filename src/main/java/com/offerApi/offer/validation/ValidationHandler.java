package com.offerApi.offer.validation;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 *
 * @author enzo
 */

@RestControllerAdvice
public class ValidationHandler {
    
     @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<ApiResponse<String>> handleUnknownProperty(UnrecognizedPropertyException ex) {
        String propertyName = ex.getPropertyName();

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Campo no reconocido: '" + propertyName + "'",
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
