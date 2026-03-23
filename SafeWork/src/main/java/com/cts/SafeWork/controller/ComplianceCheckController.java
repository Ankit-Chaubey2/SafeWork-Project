package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.ComplianceCheck;
import com.cts.SafeWork.service.IComplianceCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compliance-checks")
public class ComplianceCheckController {

    @Autowired
    private IComplianceCheckService complianceCheckService;


    @PostMapping("/createComplianceCheck")
    public ResponseEntity<ComplianceCheck> createComplianceCheck(@RequestBody ComplianceCheck complianceCheck) {
        log.info("Request received to create compliance check: {}", complianceCheck);
        ComplianceCheck createdCheck = complianceCheckService.createComplianceCheck(complianceCheck);
        log.info("Successfully created compliance check with ID: {}", createdCheck.getCheckId());
        return new ResponseEntity<>(createdCheck, HttpStatus.CREATED);
    }


    @GetMapping("getChecksByInspectionId/inspection/{inspectionId}")
    public ResponseEntity<List<ComplianceCheck>> getChecksByInspectionId(@PathVariable long inspectionId) {
        log.info("Fetching compliance checks for Inspection ID: {}", inspectionId);
        List<ComplianceCheck> checks = complianceCheckService.getChecksByInspectionId(inspectionId);
        log.info("Found {} checks for Inspection ID: {}", checks.size(), inspectionId);
        return new ResponseEntity<>(checks, HttpStatus.OK);
    }


    @GetMapping("/getAllComplianceChecks")
    public ResponseEntity<List<ComplianceCheck>> getAllComplianceChecks() {
        log.info("Fetching all compliance checks");
        List<ComplianceCheck> allChecks = complianceCheckService.getAllComplianceChecks();
        log.info("Total compliance checks retrieved: {}", allChecks.size());
        return new ResponseEntity<>(allChecks, HttpStatus.OK);
    }

    @DeleteMapping("/deleteComplianceCheck/{id}")
    public ResponseEntity<String> deleteComplianceCheck(@PathVariable long id) {
        log.warn("Request received to delete compliance check ID: {}", id);
        boolean isDeleted = complianceCheckService.deleteComplianceCheck(id);
        if (isDeleted) {
            log.info("Compliance check ID: {} deleted successfully", id);
            return new ResponseEntity<>("Compliance check deleted successfully", HttpStatus.OK);
        } else {
            log.error("Delete failed. Compliance check ID: {} not found", id);
            return new ResponseEntity<>("Check not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateComplianceCheck/{id}")
    public ResponseEntity<ComplianceCheck> updateComplianceCheck(@PathVariable long id, @RequestBody ComplianceCheck details) {
        log.info("Request received to update compliance check ID: {} with data: {}", id, details);
        try {
            ComplianceCheck updated = complianceCheckService.updateComplianceCheck(id, details);
            log.info("Successfully updated compliance check ID: {}", id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Update failed for check ID: {}. Error: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}