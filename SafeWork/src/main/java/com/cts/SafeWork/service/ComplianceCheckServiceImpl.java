package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.ComplianceCheck;
import com.cts.SafeWork.repository.ComplianceCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceCheckServiceImpl implements IComplianceCheckService {

    @Autowired
    private ComplianceCheckRepository complianceCheckRepository;

    @Override
    public ComplianceCheck createComplianceCheck(ComplianceCheck complianceCheck) {
        // Logs specific compliance results and notes [cite: 53, 88]
        return complianceCheckRepository.save(complianceCheck);
    }

    @Override
    public List<ComplianceCheck> getChecksByInspectionId(long inspectionId) {
        // This will now find the list based on the Inspection entity's ID
        return complianceCheckRepository.findByInspection_InspectionId(inspectionId);
    }

    @Override
    public List<ComplianceCheck> getAllComplianceChecks() {
        // Used for auditing and monitoring adherence [cite: 20, 96]
        return complianceCheckRepository.findAll();
    }
}