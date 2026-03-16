package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.ComplianceRecord;
import com.cts.SafeWork.enums.ComplianceEntityType;
import com.cts.SafeWork.enums.ComplianceResult;

import java.util.List;
import java.util.Optional;

public interface IComplianceRecordService {

    void createComplianceRecord(ComplianceRecord complianceRecord);

    List<ComplianceRecord> getAllComplianceRecords();

    Optional<ComplianceRecord> getComplianceRecordById(Long complianceId);

    void updateComplianceRecord(Long id, ComplianceRecord updatedRecord);

    void deleteComplianceRecord(Long complianceId);

    List<ComplianceRecord> findByEntityType(ComplianceEntityType entityType);

    List<ComplianceRecord> findByComplianceResult(ComplianceResult complianceResult);


}
