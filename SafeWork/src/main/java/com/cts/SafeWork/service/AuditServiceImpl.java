package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Audit;
import com.cts.SafeWork.enums.AuditScope;
import com.cts.SafeWork.enums.AuditStatus;
import com.cts.SafeWork.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditServiceImpl implements IAuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void createAudit(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    public List<Audit> getAllAudits() {
        return auditRepository.findAll();
    }

    @Override
    public Optional<Audit> getAuditById(Long id) {
        return auditRepository.findById(id);
    }

    @Override
    public Audit updateAudit(Long id, Audit updatedAudit) {
        return auditRepository.findById(id)
                .map(existing -> {
                    existing.setAuditScope(updatedAudit.getAuditScope());
                    existing.setAuditFinding(updatedAudit.getAuditFinding());
                    existing.setAuditDate(updatedAudit.getAuditDate());
                    existing.setAuditStatus(updatedAudit.getAuditStatus());
                    existing.setOfficer(updatedAudit.getOfficer());
                    return auditRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Audit not found with id " + id));
    }

    @Override
    public void deleteAudit(Long id) {
        auditRepository.deleteById(id);
    }

    @Override
    public List<Audit> findByAuditStatus(AuditStatus auditStatus) {

        return auditRepository.findByAuditStatus(auditStatus);
    }

    @Override
    public List<Audit> findByAuditScope(AuditScope auditScope) {
        return auditRepository.findByAuditScope(auditScope);
    }

    @Override
    public List<Audit> findByOfficer_UserId(Long userId) {
        return auditRepository.findByOfficer_UserId(userId);
    }

}
