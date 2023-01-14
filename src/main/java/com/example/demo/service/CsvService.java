package com.example.demo.service;

import com.example.demo.data.CsvData;
import com.example.demo.data.CsvRepository;
import com.example.demo.exceptions.CsvReadingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CsvService {

    private final CsvReader csvReader;
    private final CsvParser csvParser;
    private final CsvRepository csvRepository;

    public CsvService(final CsvReader csvReader, final CsvParser csvParser, final CsvRepository csvRepository) {
        this.csvReader = csvReader;
        this.csvParser = csvParser;
        this.csvRepository = csvRepository;
    }

    public List<CsvData> saveFile(final MultipartFile file){
        final var csvData= readFile(file);
        csvRepository.saveAll(csvData);
        return csvData;

    }

    public List<CsvData> readFile(final MultipartFile file) {

        try {
           return csvReader.read(file.getInputStream());
        } catch (final IOException e) {
            throw new CsvReadingException(e);
        }
    }

    public String getAllCsvdataAsCsv() throws IOException {
        final var allData = csvRepository.findAll();
        return csvParser.parseData(allData);

    }
}
