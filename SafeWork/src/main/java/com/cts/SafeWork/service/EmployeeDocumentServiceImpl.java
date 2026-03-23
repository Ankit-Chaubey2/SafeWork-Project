package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.DocumentRequestDTO;
import com.cts.SafeWork.dto.DocumentResponseDTO;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.EmployeeDocument;
import com.cts.SafeWork.exception.DocumentNotFoundException;
import com.cts.SafeWork.exception.EmployeeNotFoundException;
import com.cts.SafeWork.repository.EmployeeDocumentRepository;
import com.cts.SafeWork.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j; // Import for Logging
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j // Lombok annotation for logging
@Service
public class EmployeeDocumentServiceImpl implements IEmployeeDocumentService {

    @Autowired
    private EmployeeDocumentRepository documentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public DocumentResponseDTO uploadDocument(DocumentRequestDTO dto) {
        log.info("Attempting to upload document for Employee ID: {}", dto.getEmployeeId());

        // 1. Check if employee exists
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> {
                    log.error("Document upload failed: Employee ID {} not found", dto.getEmployeeId());
                    return new EmployeeNotFoundException(dto.getEmployeeId());
                });

        // 2. Mapping DTO to Entity
        EmployeeDocument doc = new EmployeeDocument();
        doc.setEmployeeDocumentType(dto.getEmployeeDocumentType());
        doc.setEmployeeFileURL(dto.getEmployeeFileURL());
        doc.setUploadedDate(LocalDate.now());
        doc.setVerificationStatus("PENDING");
        doc.setEmployee(employee);

        // 3. Save
        EmployeeDocument savedDoc = documentRepository.save(doc);
        log.info("Document of type {} uploaded successfully with ID: {}",
                savedDoc.getEmployeeDocumentType(), savedDoc.getEmployeeDocumentID());

        return mapToDTO(savedDoc);
    }

    @Override
    public List<DocumentResponseDTO> getDocumentsByEmployee(long employeeId) {
        log.info("Fetching all documents for Employee ID: {}", employeeId);

        List<EmployeeDocument> docs = documentRepository.findByEmployee_EmployeeId(employeeId);
        if (docs.isEmpty()) {
            log.warn("No documents found in database for Employee ID: {}", employeeId);
            throw new DocumentNotFoundException("No documents found for Employee ID: " + employeeId);
        }

        log.info("Successfully retrieved {} documents for Employee ID: {}", docs.size(), employeeId);
        return docs.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public DocumentResponseDTO getDocumentById(Long docId) {
        log.info("Fetching document details for Doc ID: {}", docId);

        EmployeeDocument doc = documentRepository.findById(docId)
                .orElseThrow(() -> {
                    log.warn("Fetch failed: Document ID {} not found", docId);
                    return new DocumentNotFoundException("Document not found with ID: " + docId);
                });

        return mapToDTO(doc);
    }

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