package com.example.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvRepository extends JpaRepository<CsvData, Long> {
}
