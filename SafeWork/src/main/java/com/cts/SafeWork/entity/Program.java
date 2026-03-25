package com.cts.SafeWork.entity;

import com.cts.SafeWork.enums.ProgramStatus;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long programId; // Changed from primitive long to wrapper Long

    private String programTitle;
    private String programDescription;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate programStartDate;   // manual entry, use ISO date format
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate programEndDate;     // manual entry, use ISO date format
    @Enumerated(EnumType.STRING)
    private ProgramStatus programStatus;

    @JsonIgnore // Prevent infinite recursion
    @OneToMany(mappedBy = "program")
    private List<Training> trainings;
}
