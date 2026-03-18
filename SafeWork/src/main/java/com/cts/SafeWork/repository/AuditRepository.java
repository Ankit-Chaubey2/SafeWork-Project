package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.Audit;
import com.cts.SafeWork.enums.AuditScope;
import com.cts.SafeWork.enums.AuditStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit,Long> {

    List<Audit> findByAuditStatus(AuditStatus auditStatus);

    List<Audit> findByAuditScope(AuditScope auditScope);

    List<Audit> findByOfficer_UserId(Long userId);


}
