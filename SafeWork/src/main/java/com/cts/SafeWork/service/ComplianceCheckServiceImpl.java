package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.ComplianceCheck;
import com.cts.SafeWork.repository.ComplianceCheckRepository;
import lombok.extern.slf4j.Slf4j; // Added import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j // Added annotation
@Service
public class ComplianceCheckServiceImpl implements IComplianceCheckService {

    @Autowired
    private ComplianceCheckRepository complianceCheckRepository;

    @Override
    public ComplianceCheck createComplianceCheck(ComplianceCheck complianceCheck) {
        log.info("Saving new compliance check to database: {}", complianceCheck.getComplianceCheckResult());
        ComplianceCheck savedCheck = complianceCheckRepository.save(complianceCheck);
        log.info("Compliance check saved successfully with ID: {}", savedCheck.getCheckId());
        return savedCheck;
    }

    @Override
    public List<ComplianceCheck> getChecksByInspectionId(long inspectionId) {
        log.info("Retrieving all compliance checks for Inspection ID: {}", inspectionId);
        List<ComplianceCheck> checks = complianceCheckRepository.findByInspection_InspectionId(inspectionId);
        log.debug("Found {} checks linked to Inspection ID: {}", checks.size(), inspectionId);
        return checks;
    }

    @Override
    public List<ComplianceCheck> getAllComplianceChecks() {
        log.info("Fetching all compliance checks for auditing");
        return complianceCheckRepository.findAll();
    }

    @Override
    public ComplianceCheck updateComplianceCheck(long checkId, ComplianceCheck details) {
        log.info("Attempting to update Compliance Check ID: {}", checkId);

        ComplianceCheck existingCheck = complianceCheckRepository.findById(checkId)
                .orElseThrow(() -> {
                    log.error("Update failed: Compliance Check ID {} not found in database", checkId);
                    return new RuntimeException("Compliance Check not found with id: " + checkId);
                });

        log.debug("Syncing updated fields for check ID: {}", checkId);
        existingCheck.setComplianceCheckResult(details.getComplianceCheckResult());
        existingCheck.setComplianceCheckNotes(details.getComplianceCheckNotes());
        existingCheck.setComplianceCheckStatus(details.getComplianceCheckStatus());
        existingCheck.setComplianceCheckDate(details.getComplianceCheckDate());

        ComplianceCheck updatedCheck = complianceCheckRepository.save(existingCheck);
        log.info("Compliance Check ID: {} updated successfully", checkId);
        return updatedCheck;
    }

    @Override
    public boolean deleteComplianceCheck(long checkId) {
        log.warn("Attempting to delete Compliance Check ID: {}", checkId);
        if (complianceCheckRepository.existsById(checkId)) {
            complianceCheckRepository.deleteById(checkId);
            log.info("Compliance Check ID: {} deleted from database", checkId);
            return true;
        }
        log.error("Deletion failed: Compliance Check ID {} does not exist", checkId);
        return false;
    }
}