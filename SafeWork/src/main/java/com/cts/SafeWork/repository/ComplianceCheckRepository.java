package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.ComplianceCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceCheckRepository extends JpaRepository<ComplianceCheck, Long> {
    List<ComplianceCheck> findByInspection_InspectionId(long inspectionId);
}
