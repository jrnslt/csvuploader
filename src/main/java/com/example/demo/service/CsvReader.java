package com.example.demo.service;

import com.example.demo.data.CsvData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReader {

    final static String[] HEADERS = {"source", "codeListCode",
            "code", "displayValue", "longDescription", "fromDate", "toDate", "sortingPriority"};


    public List<CsvData> read(InputStream inputStream) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<CsvData> csvs = new ArrayList<CsvData>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                CsvData csvData = CsvData.builder()
                        .source(csvRecord.get("source"))
                        .codeListCode(csvRecord.get("codeListCode"))
                        .code(csvRecord.get("code"))
                        .displayValue(csvRecord.get("displayValue"))
                        .longDescription(csvRecord.get("longDescription"))
                        .fromDate(csvRecord.get("fromDate"))
                        .toDate(csvRecord.get("toDate"))
                        .sortingPriorty(csvRecord.get("sortingPriority"))
                        .build();

                csvs.add(csvData);
            }

            return csvs;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
