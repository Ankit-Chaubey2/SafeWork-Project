package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.ComplianceCheck;
import com.cts.SafeWork.service.IComplianceCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Module 4.4: Safety Inspection & Compliance Tracking.
 * Manages the results and notes of specific compliance checks. [cite: 48, 53]
 */
@RestController
@RequestMapping("/api/compliance-checks")
public class ComplianceCheckController {

    @Autowired
    private IComplianceCheckService complianceCheckService;

    /**
     * Creates a new compliance check record.
     * Captures Result, Notes, and Status for an inspection. [cite: 53, 88]
     */
    @PostMapping
    public ResponseEntity<ComplianceCheck> createComplianceCheck(@RequestBody ComplianceCheck complianceCheck) {
        ComplianceCheck createdCheck = complianceCheckService.createComplianceCheck(complianceCheck);
        return new ResponseEntity<>(createdCheck, HttpStatus.CREATED);
    }

    /**
     * Retrieves all compliance checks associated with a specific inspection ID.
     * Useful for safety officers to review all findings of a single site visit. [cite: 9, 53]
     */
    @GetMapping("/inspection/{inspectionId}")
    public ResponseEntity<List<ComplianceCheck>> getChecksByInspectionId(@PathVariable long inspectionId) {
        List<ComplianceCheck> checks = complianceCheckService.getChecksByInspectionId(inspectionId);
        return new ResponseEntity<>(checks, HttpStatus.OK);
    }

    /**
     * Retrieves all compliance checks in the system.
     * Primarily used by Compliance Officers and Government Auditors for broad oversight. [cite: 12, 13, 96]
     */
    @GetMapping
    public ResponseEntity<List<ComplianceCheck>> getAllComplianceChecks() {
        List<ComplianceCheck> allChecks = complianceCheckService.getAllComplianceChecks();
        return new ResponseEntity<>(allChecks, HttpStatus.OK);
    }
}