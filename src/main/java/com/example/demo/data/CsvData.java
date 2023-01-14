package com.example.demo.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "csvdata")
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)

public class CsvData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "codeListCode")
    private String codeListCode;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "displayValue")
    private String displayValue;

    @Column(name = "longDescription")
    private String longDescription;

    @Column(name = "toDate")
    private String toDate;

    @Column(name = "fromDate")
    private String fromDate;

    @Column(name = "sortingPriorty")
    private String sortingPriorty;


    public CsvData() {
        //needed for JPA
    }
}
