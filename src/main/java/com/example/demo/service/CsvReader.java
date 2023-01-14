package com.example.demo.service;

import com.example.demo.data.CsvData;
import com.example.demo.exceptions.CsvReadingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class CsvReader {

    public List<CsvData> read(final InputStream inputStream) {

        try (final BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));
             final CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.Builder
                             .create()
                             .setHeader()
                             .setSkipHeaderRecord(true)
                             .setIgnoreEmptyLines(true)
                             .setTrim(true)
                             .build())
        ) {


            final Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            final List<CsvData> csvs = new ArrayList<>();
            csvRecords.forEach(csv ->
                    csvs.add(createCsvDataFromRecord(csv))
            );

            return Collections.unmodifiableList(csvs);
        } catch (final IOException e) {
            throw new CsvReadingException("failed to read CSV file: " + e.getMessage());
        }
    }

    private CsvData createCsvDataFromRecord(final CSVRecord csvRecord) {
        return CsvData.builder()
                .source(csvRecord.get("source"))
                .codeListCode(csvRecord.get("codeListCode"))
                .code(csvRecord.get("code"))
                .displayValue(csvRecord.get("displayValue"))
                .longDescription(csvRecord.get("longDescription"))
                .fromDate(csvRecord.get("fromDate"))
                .toDate(csvRecord.get("toDate"))
                .sortingPriorty(csvRecord.get("sortingPriority"))
                .build();
    }
}
