package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.DocumentRequestDTO;
import com.cts.SafeWork.dto.DocumentResponseDTO;
import java.util.List;

public interface IEmployeeDocumentService {
    DocumentResponseDTO uploadDocument(DocumentRequestDTO dto);
    List<DocumentResponseDTO> getDocumentsByEmployee(long employeeId);
    DocumentResponseDTO getDocumentById(Long docId);
}