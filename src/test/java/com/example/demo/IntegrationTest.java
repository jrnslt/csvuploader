package com.example.demo;


import com.example.demo.data.CsvData;
import com.example.demo.data.CsvRepository;
import com.example.demo.service.CsvReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private CsvRepository csvRepository;

    @Autowired
    private CsvReader csvReader;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        final var inputStream = this.getClass().getClassLoader().getResourceAsStream("example.csv");
        final List<CsvData> csvDataList = csvReader.read(inputStream);
        csvRepository.saveAll(csvDataList);
    }

    @AfterEach
    void tearDown() {

        csvRepository.deleteAll();
    }

    @Test
    void getcsv() throws Exception {
        final var result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/csv")
                ).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getcsvById() throws Exception {
        final var result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/csv/271636001")
                ).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getcsvByNoteexistingIdReturnsNotFound() throws Exception {
        final var result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/csv/18y3191")
                ).andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteAllRemovesAllData() throws Exception {
        final var result = this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/csv")
                ).andExpect(status().isOk())
                .andReturn();

        assertThat(csvRepository.findAll()).isEmpty();
    }

    @Test
    void saveFileReturnsOk() throws Exception {
        //clean db first
        csvRepository.deleteAll();
        //Make sure db is empty
        assertThat(csvRepository.findAll()).isEmpty();

        final var inputStream = this.getClass().getClassLoader().getResourceAsStream("example.csv");

        final String csvAsString = "source,codeListCode,code,displayValue,longDescription,fromDate,toDate,sortingPriority\n" +
                "ZIB,ZIB001,271636001,Polsslag regelmatig,The long description is necessary,01-01-2019,,1";

        final MockMultipartFile jsonFile = new MockMultipartFile("file", "example.csv", "application/csv", inputStream);

        this.mockMvc.perform(
                MockMvcRequestBuilders.multipart("/upload").file(jsonFile)
        ).andExpect(status().isOk());

        assertThat(csvRepository.findAll()).hasSize(18);
    }

}
