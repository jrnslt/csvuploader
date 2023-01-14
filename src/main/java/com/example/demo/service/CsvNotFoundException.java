package com.example.demo.service;

public class CsvNotFoundException extends RuntimeException {

    public CsvNotFoundException(final String message) {
        super(message);
    }
}
