package com.example.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CsvRepository extends JpaRepository<CsvData, Long> {

    List<CsvData> findCsvDataByCodeIgnoreCase(final String code);
}
