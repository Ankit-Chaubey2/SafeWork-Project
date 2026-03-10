package com.cts.SafeWork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeDocumentID;

    private String employeeDocumentType;
    private String employeeFileURL;
    private Date uploadedDate;
    private String verificationStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    private Employee employee;
}
