package com.api.product_control.exceptions;

import com.api.product_control.dtos.ExceptionRecordDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ExceptionRecordDto> handleProductAlreadyExists(@NotNull ProductAlreadyExistsException ex){
        var exceptionRecordDto = new ExceptionRecordDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionRecordDto);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionRecordDto> handleProductNotFound(@NotNull ProductNotFoundException ex){
        var exceptionRecordDto = new ExceptionRecordDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionRecordDto);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionRecordDto> handleGenericException(){
        var exceptionRecordDto = new ExceptionRecordDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionRecordDto);
    }
}
