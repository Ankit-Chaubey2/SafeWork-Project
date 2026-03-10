package com.cts.SafeWork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeId;

    private String employeeName;
    private Date employeeDOB;
    private String employeeGender;
    private String employeeAddress;
    private String employeeContact;
    private String employeeDepartmentName;
    private String employeeStatus;

    @OneToMany(mappedBy = "employee")
    private List<Hazard> hazards;

    @OneToMany(mappedBy = "employee")
    private List<Training> trainings;

    @OneToMany(mappedBy = "employee")
    private List<EmployeeDocument> documents;
}
