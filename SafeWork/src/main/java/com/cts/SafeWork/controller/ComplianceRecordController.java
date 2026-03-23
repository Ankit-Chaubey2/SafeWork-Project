package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.ComplianceRecord;
import com.cts.SafeWork.enums.ComplianceEntityType;
import com.cts.SafeWork.enums.ComplianceResult;
import com.cts.SafeWork.service.IComplianceRecordService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/complianceRecord")
public class ComplianceRecordController {

    private final IComplianceRecordService complianceRecordService;

    @Autowired
    public ComplianceRecordController(IComplianceRecordService complianceRecordService) {
        this.complianceRecordService = complianceRecordService;
    }


    @PostMapping("/createComplianceRecord")
    public ResponseEntity<String> createComplianceRecord(@RequestBody ComplianceRecord complianceRecord) {
        complianceRecordService.createComplianceRecord(complianceRecord);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Compliance Record created successfully");
    }

    @PutMapping("/updateComplianceRecord/{complianceId}")
    public ResponseEntity<String> updateComplianceRecord(
            @PathVariable Long complianceId,
            @RequestBody ComplianceRecord updatedRecord) {

        complianceRecordService.updateComplianceRecord(complianceId, updatedRecord);
        return ResponseEntity.ok("Compliance Record updated successfully");
    }


    @DeleteMapping("/deleteComplianceRecord/{complianceId}")
    public ResponseEntity<String> deleteComplianceRecord(@PathVariable Long complianceId) {
        complianceRecordService.deleteComplianceRecord(complianceId);
        return ResponseEntity.ok("Compliance Record deleted successfully");
    }

    @GetMapping("/getAllComplianceRecord")
    public ResponseEntity<List<ComplianceRecord>> getAllComplianceRecords() {
        List<ComplianceRecord> records = complianceRecordService.getAllComplianceRecords();
        return ResponseEntity.ok(records);
    }


    @GetMapping("/getComplianceRecordById/{complianceId}")
    public ResponseEntity<?> getComplianceRecordById(@PathVariable Long complianceId) {
        Optional<ComplianceRecord> recordOpt =
                complianceRecordService.getComplianceRecordById(complianceId);

        if (recordOpt.isPresent()) {
            return ResponseEntity.ok(recordOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Record not found with complianceId: " + complianceId);
        }
    }


    @GetMapping("/ComplianceEntityType/{entityType}")
    public ResponseEntity<List<ComplianceRecord>> findByEntityType(
            @PathVariable ComplianceEntityType entityType) {

        return ResponseEntity.ok(complianceRecordService.findByEntityType(entityType));
    }

    @GetMapping("/findByComplianceResult/{complianceResult}")
    public ResponseEntity<List<ComplianceRecord>> findByComplianceResult(
            @PathVariable ComplianceResult complianceResult) {

    @GetMapping("/findByComplianceResult/{complianceResult}")
    public ResponseEntity<List<ComplianceRecord>> findByComplianceResult(
            @PathVariable ComplianceResult complianceResult) {

        return ResponseEntity.ok(complianceRecordService.findByComplianceResult(complianceResult));
    }
}