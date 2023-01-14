package com.example.demo.resource;


import com.example.demo.service.CsvService;
import com.example.demo.data.CsvData;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CsvController {

    private final CsvService csvService;

    public CsvController(final CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping(value = "/csv", produces = "application/csv")
    public ResponseEntity<ByteArrayResource> getAllCsvData() throws IOException {
        final var csv = csvService.getAllCsvdataAsCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=allData.csv")
                .body(new ByteArrayResource(csv.getBytes()));
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<List<CsvData>> uploadFile(@RequestParam("file") final MultipartFile file) {
        final var result = csvService.saveFile(file);
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.internalServerError().body(List.of());
    }
}
