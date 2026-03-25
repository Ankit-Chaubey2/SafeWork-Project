package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.DocumentRequestDTO;
import com.cts.SafeWork.dto.DocumentResponseDTO;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.EmployeeDocument;
import com.cts.SafeWork.exception.DocumentNotFoundException;
import com.cts.SafeWork.exception.EmployeeNotFoundException;
import com.cts.SafeWork.repository.EmployeeDocumentRepository;
import com.cts.SafeWork.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeDocumentServiceImpl implements IEmployeeDocumentService {

    @Autowired
    private EmployeeDocumentRepository documentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    private void validateOwnership(Employee targetEmployee) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new RuntimeException("User is not authenticated");
        }

        String loggedInUserEmail = auth.getName();

        boolean isPrivileged = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN") ||
                        a.getAuthority().equals("ROLE_ADMIN") ||
                        a.getAuthority().equals("HAZARD_OFFICER") ||
                        a.getAuthority().equals("ROLE_HAZARD_OFFICER"));


        if (!isPrivileged && !targetEmployee.getEmail().equalsIgnoreCase(loggedInUserEmail)) {
            log.error("SECURITY VIOLATION: User {} tried to access documents of {}", loggedInUserEmail, targetEmployee.getEmail());
            throw new RuntimeException("Access Denied: You can only manage your own documents.");
        }
    }

    @Override
    @Transactional
    public DocumentResponseDTO uploadDocument(DocumentRequestDTO dto) {
        log.info("Request to upload document for Employee ID: {}", dto.getEmployeeId());

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(dto.getEmployeeId()));

        // SECURITY: Check if user is allowed to upload for this employee ID
        validateOwnership(employee);

        EmployeeDocument doc = new EmployeeDocument();
        doc.setEmployeeDocumentType(dto.getEmployeeDocumentType());
        doc.setEmployeeFileURL(dto.getEmployeeFileURL());
        doc.setUploadedDate(LocalDate.now());
        doc.setVerificationStatus("PENDING");
        doc.setEmployee(employee);

        EmployeeDocument savedDoc = documentRepository.save(doc);
        log.info("Document saved successfully for: {}", employee.getEmail());

        return mapToDTO(savedDoc);
    }

    @Override
    public List<DocumentResponseDTO> getDocumentsByEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        // SECURITY: Prevents Employee 2 from fetching the list for Employee 1
        validateOwnership(employee);

        List<EmployeeDocument> docs = documentRepository.findByEmployee_EmployeeId(employeeId);
        return docs.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public DocumentResponseDTO getDocumentById(Long docId) {
        log.info("Security Check: Fetching document ID: {}", docId);

        // 1. Fetch the document from the Database
        EmployeeDocument doc = documentRepository.findById(docId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with ID: " + docId));

        // 2. Get the currently logged-in user's Email from the Token
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // 3. Find the Employee record for the logged-in user to get THEIR real ID
        Employee loggedInEmployee = employeeRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new EmployeeNotFoundException("Logged-in user not found in database."));

        // 4. THE MASTER SECURITY CHECK:
        // Does the ID of the document's owner match the ID of the person logged in?
        Long ownerId = doc.getEmployee().getEmployeeId();
        Long requesterId = loggedInEmployee.getEmployeeId();

        log.info("Comparing Owner ID: {} with Requester ID: {}", ownerId, requesterId);

        if (!Objects.equals(ownerId, requesterId)) {
            log.error("SECURITY BREACH: Employee {} tried to access Document {} owned by Employee {}",
                    requesterId, docId, ownerId);
            throw new RuntimeException("Access Denied: This document does not belong to your Employee ID.");
        }

        // 5. If they match, proceed
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