package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.ComplianceCheck;
import java.util.List;

public interface IComplianceCheckService {
    ComplianceCheck createComplianceCheck(ComplianceCheck complianceCheck); //
    List<ComplianceCheck> getChecksByInspectionId(long inspectionId); //
    List<ComplianceCheck> getAllComplianceChecks();
}