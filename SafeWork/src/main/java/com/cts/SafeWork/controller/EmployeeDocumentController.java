


package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.EmployeeDocument;
import com.cts.SafeWork.service.IEmployeeDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class EmployeeDocumentController {

    @Autowired
    private IEmployeeDocumentService documentService;

    // 1. Upload/Save Document metadata
    @PostMapping("/upload")
    public ResponseEntity<EmployeeDocument> upload(@RequestBody EmployeeDocument document) {
        EmployeeDocument savedDoc = documentService.uploadDocument(document);
        return new ResponseEntity<>(savedDoc, HttpStatus.CREATED);
    }

    // 2. Get all documents for a specific Employee
    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<EmployeeDocument>> getByEmployee(@PathVariable long empId) {
        List<EmployeeDocument> documents = documentService.getDocumentsByEmployee(empId);
        return ResponseEntity.ok(documents);
    }
}