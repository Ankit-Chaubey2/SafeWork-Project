package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.ComplianceRecord;
import com.cts.SafeWork.enums.ComplianceEntityType;
import com.cts.SafeWork.enums.ComplianceResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord,Long> {

    List<ComplianceRecord> findByEntityType(ComplianceEntityType entityType);
    List<ComplianceRecord> findByComplianceResult(ComplianceResult complianceResult);

}
