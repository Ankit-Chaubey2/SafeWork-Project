package com.cts.SafeWork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DocumentRequestDTO {
    @NotBlank(message = "Document type is required")
    private String employeeDocumentType;
    private String employeeFileURL;
    @NotNull(message = "Employee ID is required")

    private long employeeId; // taking id only
}