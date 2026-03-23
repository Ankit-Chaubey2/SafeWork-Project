package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Audit;
import com.cts.SafeWork.enums.AuditScope;
import com.cts.SafeWork.enums.AuditStatus;
import com.cts.SafeWork.projection.AuditByIdProjection;

import java.util.List;
import java.util.Optional;

public interface IAuditService {

    void createAudit(Audit audit);

    List<Audit> getAllAudits();

    Optional<AuditByIdProjection> getAuditById(Long id);

    Audit updateAudit(Long id, Audit updatedAudit);

    void deleteAudit(Long id);

    List<Audit> findByAuditStatus(AuditStatus auditStatus);

    List<Audit> findByAuditScope(AuditScope auditScope);

    List<Audit> findAuditByOfficer_UserId(Long userId);

}
