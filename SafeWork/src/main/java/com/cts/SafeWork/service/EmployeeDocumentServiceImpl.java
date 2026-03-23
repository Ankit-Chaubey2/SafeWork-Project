package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.DocumentRequestDTO;
import com.cts.SafeWork.dto.DocumentResponseDTO;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.EmployeeDocument;
import com.cts.SafeWork.exception.DocumentNotFoundException;
import com.cts.SafeWork.exception.EmployeeNotFoundException;
import com.cts.SafeWork.repository.EmployeeDocumentRepository;
import com.cts.SafeWork.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeDocumentServiceImpl implements IEmployeeDocumentService {

    @Autowired
    private EmployeeDocumentRepository documentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public DocumentResponseDTO uploadDocument(DocumentRequestDTO dto) {
        // 1. look for employee if not then throw 404
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

        // 2. Mapping: DTO to Entity
        EmployeeDocument doc = new EmployeeDocument();
        doc.setEmployeeDocumentType(dto.getEmployeeDocumentType());
        doc.setEmployeeFileURL(dto.getEmployeeFileURL());
        doc.setUploadedDate(LocalDate.now());
        doc.setVerificationStatus("PENDING");
        doc.setEmployee(employee);

        // 3. Save and return Response DTO
        EmployeeDocument savedDoc = documentRepository.save(doc);
        return mapToDTO(savedDoc);
    }

    @Override
    public List<DocumentResponseDTO> getDocumentsByEmployee(long employeeId) {
        List<EmployeeDocument> docs = documentRepository.findByEmployee_EmployeeId(employeeId);
        if (docs.isEmpty()) {
            throw new DocumentNotFoundException("No documents found for Employee ID: " + employeeId);
        }
        return docs.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public DocumentResponseDTO getDocumentById(Long docId) {
        EmployeeDocument doc = documentRepository.findById(docId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with ID: " + docId));
        return mapToDTO(doc);
    }

    // Helper: Entity -> DTO
    private DocumentResponseDTO mapToDTO(EmployeeDocument doc) {
        DocumentResponseDTO dto = new DocumentResponseDTO();
        dto.setEmployeeDocumentID(doc.getEmployeeDocumentID());
        dto.setEmployeeDocumentType(doc.getEmployeeDocumentType());
        dto.setEmployeeFileURL(doc.getEmployeeFileURL());
        dto.setUploadedDate(doc.getUploadedDate());
        dto.setVerificationStatus(doc.getVerificationStatus());
        return dto;
    }
}