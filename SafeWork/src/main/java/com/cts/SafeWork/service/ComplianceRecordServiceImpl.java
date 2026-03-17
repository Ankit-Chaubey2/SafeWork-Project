package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.ComplianceRecord;
import com.cts.SafeWork.enums.ComplianceEntityType;
import com.cts.SafeWork.enums.ComplianceResult;
import com.cts.SafeWork.repository.ComplianceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplianceRecordServiceImpl implements IComplianceRecordService {

    @Autowired
    private ComplianceRecordRepository complianceRecordRepository;

    @Override
    public void createComplianceRecord(ComplianceRecord complianceRecord) {
        complianceRecordRepository.save(complianceRecord);
    }

    @Override
    public List<ComplianceRecord> getAllComplianceRecords() {

        return complianceRecordRepository.findAll();
    }

    @Override
    public Optional<ComplianceRecord> getComplianceRecordById(Long id) {
        return complianceRecordRepository.findById(id);
    }

    @Override
    public void updateComplianceRecord(Long id, ComplianceRecord updatedRecord) {
        complianceRecordRepository.findById(id)
                .map(existing -> {
                    existing.setEntityId(updatedRecord.getEntityId());
                    existing.setEntityType(updatedRecord.getEntityType());
                    existing.setComplianceResult(updatedRecord.getComplianceResult());
                    existing.setComplianceDate(updatedRecord.getComplianceDate());
                    existing.setComplianceNotes(updatedRecord.getComplianceNotes());
                    return complianceRecordRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ComplianceRecord not found with id " + id));
    }

    @Override
    public void deleteComplianceRecord(Long id) {
        complianceRecordRepository.deleteById(id);
    }

    @Override
    public List<ComplianceRecord> findByEntityType(ComplianceEntityType entityType) {
        return complianceRecordRepository.findByEntityType(entityType);
    }

    @Override
    public List<ComplianceRecord> findByComplianceResult(ComplianceResult complianceResult) {

        return complianceRecordRepository.findByComplianceResult(complianceResult);
    }


}
