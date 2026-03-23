package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.ComplianceCheck;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


public interface IComplianceCheckService {
    ComplianceCheck createComplianceCheck(ComplianceCheck complianceCheck); //
    List<ComplianceCheck> getChecksByInspectionId(long inspectionId); //
    List<ComplianceCheck> getAllComplianceChecks();

    ComplianceCheck updateComplianceCheck(long checkId, ComplianceCheck details);
    boolean deleteComplianceCheck(long checkId);
}