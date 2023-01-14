package com.example.demo.exceptions;

public class CsvParsingException extends RuntimeException {

    public CsvParsingException(final String message) {
        super(message);
    }
}
