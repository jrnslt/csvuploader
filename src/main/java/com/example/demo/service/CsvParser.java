package com.example.demo.service;

import com.example.demo.data.CsvData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class CsvParser {

    final static String[] HEADERS = {"source", "codeListCode",
            "code", "displayValue", "longDescription", "fromDate", "toDate", "sortingPriority"};

    public String parseData(List<CsvData> allData) throws IOException {

        final var out = new ByteArrayOutputStream();
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT);
            printer.printRecord(HEADERS);
            for (CsvData csvData : allData) {
                printer.printRecord(printAllFieldsToLine(csvData));
            }

        printer.flush();
        return out.toString();

    }

    private List<String> printAllFieldsToLine(final CsvData csvData) {
        return  List.of(
                csvData.getSource(),
                csvData.getCodeListCode(),
                csvData.getCode(),
                csvData.getDisplayValue(),
                csvData.getLongDescription(),
                csvData.getFromDate(),
                csvData.getToDate(),
                csvData.getSortingPriorty());


    }


}