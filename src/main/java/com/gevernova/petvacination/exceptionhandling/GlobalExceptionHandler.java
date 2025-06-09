package com.gevernova.petvacination.exceptionhandling;

import com.gevernova.petvacination.dto.ResponseDTO; // Make sure this import is correct
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // This annotation combines @ControllerAdvice and @ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ResponseDTO> handlePetNotFoundException(PetNotFoundException petNotFoundException) {
        logger.warn("PetNotFoundException: {}", petNotFoundException.getMessage());
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .message(petNotFoundException.getMessage())
                        .data(null)
                        .build(),
                HttpStatus.NOT_FOUND // 404
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        logger.warn("Validation failed: {}", errors);
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .message("Validation failed")
                        .data(errors) // Return detailed errors in the data field
                        .build(),
                HttpStatus.BAD_REQUEST // 400
        );
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        logger.warn("Malformed JSON or unreadable HTTP message: {}", httpMessageNotReadableException.getMessage());
        String errorMessage = "Malformed JSON request or unreadable message. Please check your input.";
        if (httpMessageNotReadableException.getCause() != null) {
            errorMessage += " Cause: " + httpMessageNotReadableException.getCause().getMessage();
        }
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .message(errorMessage)
                        .data(null)
                        .build(),
                HttpStatus.BAD_REQUEST // 400
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGlobalException(Exception exception) {
        logger.error("An unexpected error occurred: {}", exception.getMessage(), exception); // Log full stack trace
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .message("An unexpected error occurred: " + exception.getMessage())
                        .data(null)
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR // 500
        );
    }
}
