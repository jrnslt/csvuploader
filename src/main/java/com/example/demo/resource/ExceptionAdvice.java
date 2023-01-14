package com.example.demo.resource;

import com.example.demo.exceptions.CsvParsingException;
import com.example.demo.exceptions.CsvReadingException;
import com.example.demo.service.CsvNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(CsvReadingException.class)
    public ResponseEntity<String> handleCsvReadingException(final CsvReadingException e){
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(CsvParsingException.class)
    public ResponseEntity<String> handleCsvParsingException(final CsvParsingException e){
        return ResponseEntity
                .internalServerError()
                .body(e.getMessage());
    }

    @ExceptionHandler(CsvNotFoundException.class)
    public ResponseEntity<String> handleCsvNotFoundException(final CsvNotFoundException e){
        return ResponseEntity.status(NOT_FOUND)
                .body(e.getMessage());
    }

}
