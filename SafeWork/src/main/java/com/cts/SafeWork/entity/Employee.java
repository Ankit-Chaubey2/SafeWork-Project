
package com.cts.SafeWork.entity;

import com.cts.SafeWork.enums.EmployeeStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String employeeName;
    private LocalDate employeeDOB;
    private String employeeGender;
    private String employeeAddress;
    private String employeeContact;
    private String employeeDepartmentName;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "hazard-employees") // Tells Jackson this is the "Parent"
    private List<Hazard> hazards;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "training-employees")
    private List<Training> trainings;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "doc-employees")
    private List<EmployeeDocument> documents;
}
