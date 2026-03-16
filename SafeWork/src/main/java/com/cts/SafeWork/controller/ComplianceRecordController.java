package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.ComplianceRecord;
import com.cts.SafeWork.enums.ComplianceEntityType;
import com.cts.SafeWork.enums.ComplianceResult;
import com.cts.SafeWork.service.ComplianceRecordServiceImpl;
import com.cts.SafeWork.service.IComplianceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complianceRecord")
public class ComplianceRecordController {


    @Autowired
    private IComplianceRecordService complianceRecordService;

    @PostMapping("/createComplianceRecord")
    public void createComplianceRecord(@RequestBody ComplianceRecord complianceRecord) {
        complianceRecordService.createComplianceRecord(complianceRecord);
    }

    @GetMapping("/getAllComplianceRecord")
    public List<ComplianceRecord> getAllComplianceRecords() {
        return complianceRecordService.getAllComplianceRecords();
    }

    @GetMapping("/getComplianceRecordById/{complianceId}")
    public ComplianceRecord getComplianceRecordById(@PathVariable Long complianceId) {
        return complianceRecordService.getComplianceRecordById(complianceId).
                orElseThrow(() -> new RuntimeException("Record not found with complianceId: " + complianceId));
    }

    @PutMapping("/updateComplianceRecord/{complianceId}")
    public void updateComplianceRecord(@PathVariable Long complianceId, @RequestBody ComplianceRecord updatedRecord) {
        complianceRecordService.updateComplianceRecord(complianceId, updatedRecord);
    }

    @DeleteMapping("/deleteComplianceRecord/{complianceId}")
    public void deleteComplianceRecord(@PathVariable Long complianceId) {
        complianceRecordService.deleteComplianceRecord(complianceId);
    }

    @GetMapping("/ComplianceEntityType/{entityType}")
    public List<ComplianceRecord> findByEntityType(@PathVariable ComplianceEntityType entityType) {
        return complianceRecordService.findByEntityType(entityType);
    }

    @GetMapping("/findByComplianceResult/{complianceResult}")
    public List<ComplianceRecord> findByComplianceResult(@PathVariable ComplianceResult complianceResult) {
        return complianceRecordService.findByComplianceResult(complianceResult);
    }


}