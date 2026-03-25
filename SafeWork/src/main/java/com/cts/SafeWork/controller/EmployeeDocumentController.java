
package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.DocumentRequestDTO;
import com.cts.SafeWork.dto.DocumentResponseDTO;
import com.cts.SafeWork.service.IEmployeeDocumentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j; // Import for Logging
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Lombok annotation
@RestController
@RequestMapping("/documents")
public class EmployeeDocumentController {

    @Autowired
    private IEmployeeDocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<DocumentResponseDTO> upload(@Valid @RequestBody DocumentRequestDTO dto) {
        log.info("REST request to upload document of type: {} for Employee: {}",
                dto.getEmployeeDocumentType(), dto.getEmployeeId());
        return new ResponseEntity<>(documentService.uploadDocument(dto), HttpStatus.CREATED);
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<DocumentResponseDTO>> getByEmployee(@PathVariable long empId) {
        log.info("REST request to get all documents for Employee ID: {}", empId);
        return ResponseEntity.ok(documentService.getDocumentsByEmployee(empId));
    }

    @GetMapping("/{docId}")
    public ResponseEntity<DocumentResponseDTO> getById(@PathVariable Long docId) {
        log.info("REST request to get document details for Doc ID: {}", docId);
        return ResponseEntity.ok(documentService.getDocumentById(docId));
    }
}