package com.example.demo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class CsvReaderTest {

    private InputStream is;

    private CsvReader csvReader;
    @BeforeEach
    void setUp() {
       is = this.getClass().getClassLoader().getResourceAsStream("example.csv");
        this.csvReader = new CsvReader();
    }

    @AfterEach
    void tearDown() throws IOException {
        is.close();
    }

    @Test
    void readCsvs() throws IOException {
        final var result = csvReader.read(is);
        assertThat(result).isNotEmpty()
                        .hasSize(18);
        assertThat(result.get(0)).satisfies(csvData -> {
            assertThat(csvData.getCode()).isEqualTo("271636001");
        });
    }
}