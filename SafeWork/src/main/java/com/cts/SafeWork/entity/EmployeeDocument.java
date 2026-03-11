package com.cts.SafeWork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeDocumentID;

    private String employeeDocumentType;
    private String employeeFileURL;
    private LocalDate uploadedDate;
    private String verificationStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference // Tells Jackson to STOP looping back to the Employee
    private Employee employee;
}