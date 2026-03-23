package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Audit;
import com.cts.SafeWork.enums.AuditScope;
import com.cts.SafeWork.enums.AuditStatus;
import com.cts.SafeWork.exception.AuditNotFoundException;
import com.cts.SafeWork.exception.NoAuditFoundException;
import com.cts.SafeWork.projection.AuditByIdProjection;
import com.cts.SafeWork.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuditServiceImpl implements IAuditService {

    private final AuditRepository auditRepository;

    @Autowired
    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public void createAudit(Audit audit) {
        log.info("Saving new audit");
        auditRepository.save(audit);
        log.info("Audit saved successfully");
    }

    @Override
    public List<Audit> getAllAudits() {
        List<Audit> audits = auditRepository.findAll();

        if (audits.isEmpty()) {
            throw new NoAuditFoundException("No audits found in the system");
        }
        return audits;
    }

    @Override
    public Optional<AuditByIdProjection> getAuditById(Long id) {
        return auditRepository.findProjectedByAuditId(id);
    }

    @Override
    public Audit updateAudit(Long id, Audit updatedAudit) {
        log.info("Updating audit {}", id);
        return auditRepository.findById(id)
                .map(existing -> {
                    existing.setAuditScope(updatedAudit.getAuditScope());
                    existing.setAuditFinding(updatedAudit.getAuditFinding());
                    existing.setAuditDate(updatedAudit.getAuditDate());
                    existing.setAuditStatus(updatedAudit.getAuditStatus());
                    existing.setOfficer(updatedAudit.getOfficer());
                    log.info("Audit {} updated", id);
                    return auditRepository.save(existing);
                })
                .orElseThrow(() ->
                        new AuditNotFoundException("Audit not found with id: " + id)
                );
    }

    @Override
    public void deleteAudit(Long id) {
        if (!auditRepository.existsById(id)) {
            throw new AuditNotFoundException("Audit not found with id: " + id);
        }
        auditRepository.deleteById(id);
        log.info("Audit {} deleted", id);
    }

    @Override
    public List<Audit> findByAuditStatus(AuditStatus auditStatus) {

        List<Audit> audits = auditRepository.findByAuditStatus(auditStatus);

        if (audits.isEmpty()) {
            throw new NoAuditFoundException(
                    "No audits found with status: " + auditStatus
            );
        }
        return audits;
    }

    @Override
    public List<Audit> findByAuditScope(AuditScope auditScope) {

        List<Audit> audits = auditRepository.findByAuditScope(auditScope);

        if (audits.isEmpty()) {
            throw new NoAuditFoundException(
                    "No audits found with scope: " + auditScope
            );
        }
        return audits;
    }


    @Override
    public List<Audit> findAuditByOfficer_UserId(Long userId) {

        List<Audit> audits = auditRepository.findAuditByOfficer_UserId(userId);

        if (audits.isEmpty()) {
            throw new NoAuditFoundException(
                    "No audits found for officer with userId: " + userId
            );
        }
        return audits;
    }
}
