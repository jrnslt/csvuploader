package com.example.demo.resource;

import com.example.demo.exceptions.CsvReadingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(CsvReadingException.class)
    public ResponseEntity<String> handleCsvReadingException(final CsvReadingException e){
        return ResponseEntity.badRequest()
                .body("Cannot read invalid csv");
    }

}
