//package com.cts.SafeWork.controller;
//
//import com.cts.SafeWork.dto.DocumentRequestDTO;
//import com.cts.SafeWork.dto.DocumentResponseDTO;
//import com.cts.SafeWork.service.IEmployeeDocumentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/documents")
//public class EmployeeDocumentController {
//
//    @Autowired
//    private IEmployeeDocumentService documentService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<DocumentResponseDTO> upload(@RequestBody DocumentRequestDTO dto) {
//        return new ResponseEntity<>(documentService.uploadDocument(dto), HttpStatus.CREATED);
//    }
//
//    @GetMapping("/employee/{empId}")
//    public ResponseEntity<List<DocumentResponseDTO>> getByEmployee(@PathVariable long empId) {
//        return ResponseEntity.ok(documentService.getDocumentsByEmployee(empId));
//    }
//
//    @GetMapping("/{docId}")
//    public ResponseEntity<DocumentResponseDTO> getById(@PathVariable Long docId) {
//        return ResponseEntity.ok(documentService.getDocumentById(docId));
//    }
//}

package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.DocumentRequestDTO;
import com.cts.SafeWork.dto.DocumentResponseDTO;
import com.cts.SafeWork.service.IEmployeeDocumentService;
import jakarta.validation.Valid; // Zaroori import
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

    // Added @Valid here to check DocumentRequestDTO
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponseDTO> upload(@Valid @RequestBody DocumentRequestDTO dto) {
        return new ResponseEntity<>(documentService.uploadDocument(dto), HttpStatus.CREATED);
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<DocumentResponseDTO>> getByEmployee(@PathVariable long empId) {
        return ResponseEntity.ok(documentService.getDocumentsByEmployee(empId));
    }

    @GetMapping("/{docId}")
    public ResponseEntity<DocumentResponseDTO> getById(@PathVariable Long docId) {
        return ResponseEntity.ok(documentService.getDocumentById(docId));
    }
}