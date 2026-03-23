package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.ComplianceRecord;
import com.cts.SafeWork.enums.ComplianceEntityType;
import com.cts.SafeWork.enums.ComplianceResult;
import com.cts.SafeWork.exception.ResourceNotFoundException;
import com.cts.SafeWork.repository.ComplianceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplianceRecordServiceImpl implements IComplianceRecordService {

    private final ComplianceRecordRepository complianceRecordRepository;

    @Autowired
    public ComplianceRecordServiceImpl(ComplianceRecordRepository complianceRecordRepository) {
        this.complianceRecordRepository = complianceRecordRepository;
    }

    @Override
    public void createComplianceRecord(ComplianceRecord complianceRecord) {
        complianceRecordRepository.save(complianceRecord);
    }

    @Override
    public List<ComplianceRecord> getAllComplianceRecords() {
        List<ComplianceRecord> records = complianceRecordRepository.findAll();

        if (records.isEmpty()) {
            throw new ResourceNotFoundException("No compliance records found");
        }

        return records;
    }

    @Override
    public Optional<ComplianceRecord> getComplianceRecordById(Long id) {
        Optional<ComplianceRecord> record = complianceRecordRepository.findById(id);

        if (record.isEmpty()) {
            throw new ResourceNotFoundException("ComplianceRecord not found with id " + id);
        }

        return record;
    }

    @Override
    public void updateComplianceRecord(Long id, ComplianceRecord updatedRecord) {
        ComplianceRecord existing = complianceRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ComplianceRecord not found with id " + id));

        existing.setEntityId(updatedRecord.getEntityId());
        existing.setEntityType(updatedRecord.getEntityType());
        existing.setComplianceResult(updatedRecord.getComplianceResult());
        existing.setComplianceDate(updatedRecord.getComplianceDate());
        existing.setComplianceNotes(updatedRecord.getComplianceNotes());

        complianceRecordRepository.save(existing);
    }

    @Override
    public void deleteComplianceRecord(Long id) {
        if (!complianceRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("ComplianceRecord not found with id " + id);
        }
        complianceRecordRepository.deleteById(id);
    }

    @Override
    public List<ComplianceRecord> findByEntityType(ComplianceEntityType entityType) {
        List<ComplianceRecord> records = complianceRecordRepository.findByEntityType(entityType);

        if (records.isEmpty()) {
            throw new ResourceNotFoundException("No records found for entity type: " + entityType);
        }

        return records;
    }

    @Override
    public List<ComplianceRecord> findByComplianceResult(ComplianceResult complianceResult) {
        List<ComplianceRecord> records = complianceRecordRepository.findByComplianceResult(complianceResult);

        if (records.isEmpty()) {
            throw new ResourceNotFoundException("No records found for result: " + complianceResult);
        }

        return records;
    }
}