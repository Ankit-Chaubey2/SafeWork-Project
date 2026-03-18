package com.cts.SafeWork.dto;

import com.cts.SafeWork.enums.EmployeeStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeResponseDTO {
    private long employeeId;
    private String email;
    private String employeeName;
    private LocalDate employeeDOB;
    private String employeeGender;
    private String employeeAddress;
    private String employeeContact;
    private String employeeDepartmentName;
    private EmployeeStatus employeeStatus;
}