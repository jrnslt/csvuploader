package com.example.demo.exceptions;

import java.io.IOException;

public class CsvReadingException extends RuntimeException {
    public CsvReadingException(final IOException e) {
    }
}
