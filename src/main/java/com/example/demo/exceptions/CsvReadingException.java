package com.example.demo.exceptions;

public class CsvReadingException extends RuntimeException {
    public CsvReadingException(final String message) {
        super(message);
    }
}
