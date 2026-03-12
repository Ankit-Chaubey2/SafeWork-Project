package com.cts.SafeWork.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long trainingId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate trainingCompletionDate;   // manual ISO date
    @Enumerated(EnumType.STRING)
    private TrainingStatus trainingStatus;

    @JsonIgnore // Prevent infinite recursion
    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "programId")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    private Employee employee;
}
