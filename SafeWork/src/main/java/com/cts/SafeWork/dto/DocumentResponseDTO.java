package com.cts.SafeWork.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DocumentResponseDTO {
    private long employeeDocumentID;
    private String employeeDocumentType;
    private String employeeFileURL;
    private LocalDate uploadedDate;
    private String verificationStatus;
}