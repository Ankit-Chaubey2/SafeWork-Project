package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {
}
