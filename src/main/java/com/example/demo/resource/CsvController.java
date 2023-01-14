package com.example.demo.resource;


import com.example.demo.service.CsvService;
import com.example.demo.data.CsvData;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/csv/{code}", produces = "application/csv")
    public ResponseEntity<ByteArrayResource> getAllCsvDataByCode(@PathVariable(name = "code") final String code) {
        final var csv = csvService.getAllCsvdataAsCsvByCode(code);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=allData.csv")
                .body(new ByteArrayResource(csv.getBytes()));
    }

    @DeleteMapping(value = "/csv", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllData(){
        final var deleted = csvService.deleteAllData();
        if (deleted){
            return ResponseEntity.ok("data deleted");
        }
        return ResponseEntity.internalServerError().body("something went wrong");
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CsvData>> uploadFile(@RequestParam("file") final MultipartFile file) {
        final var result = csvService.saveFile(file);
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.internalServerError().body(List.of());
    }


}
