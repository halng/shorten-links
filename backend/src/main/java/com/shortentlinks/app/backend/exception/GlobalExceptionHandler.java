package com.shortentlinks.app.backend.exception;

import com.shortentlinks.app.backend.dto.ResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String NOT_FOUND = "Not found";

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ResDTO> handleNotFoundException(NotFoundException e) {
        ResDTO resDTO = new ResDTO(NOT_FOUND, e.getMessage());
        return ResponseEntity.ok(resDTO);
    }
}
