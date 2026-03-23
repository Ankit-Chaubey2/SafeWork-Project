package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.Audit;
import com.cts.SafeWork.enums.AuditScope;
import com.cts.SafeWork.enums.AuditStatus;
import com.cts.SafeWork.projection.AuditByIdProjection;
import com.cts.SafeWork.service.IAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {


    private final IAuditService auditService;

    @Autowired
    public AuditController(IAuditService auditService) {
        this.auditService = auditService;
    }


    @PostMapping("/createAudit")
    public ResponseEntity<String> createAudit(@RequestBody Audit audit) {
        auditService.createAudit(audit);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Audit created successfully");
    }


    @PutMapping("/updateAudit/{auditId}")
    public ResponseEntity<Audit> updateAudit(
            @PathVariable Long auditId,
            @RequestBody Audit updatedAudit) {
        Audit audit = auditService.updateAudit(auditId, updatedAudit);
        if (audit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(audit);
    }

    @GetMapping("/getAllAudits")
    public ResponseEntity<List<Audit>> getAllAudits() {
        List<Audit> audits = auditService.getAllAudits();
        return ResponseEntity.ok(audits);
    }

    @GetMapping("/getAuditById/{auditId}")
    public ResponseEntity<AuditByIdProjection> getAuditById(@PathVariable Long auditId) {
        return auditService.getAuditById(auditId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByAuditStatus/{auditStatus}")
    public ResponseEntity<List<Audit>> findByAuditStatus(@PathVariable AuditStatus auditStatus) {
        List<Audit> audits = auditService.findByAuditStatus(auditStatus);
        return ResponseEntity.ok(audits);
    }

    @GetMapping("/findByAuditScope/{auditScope}")
    public ResponseEntity<List<Audit>> findByAuditScope(@PathVariable AuditScope auditScope) {
        List<Audit> audits = auditService.findByAuditScope(auditScope);
        return ResponseEntity.ok(audits);
    }

    @GetMapping("/findAuditByOfficer_UserId/{userId}")
    public ResponseEntity<List<Audit>> findAuditByOfficer_UserId(@PathVariable Long userId) {
        List<Audit> audits = auditService.findAuditByOfficer_UserId(userId);
        return ResponseEntity.ok(audits);
    }

    @DeleteMapping("/deleteAudit/{auditId}")
    public ResponseEntity<String> deleteAudit(@PathVariable Long auditId) {
        auditService.deleteAudit(auditId);
        return ResponseEntity.ok("Audit deleted successfully");
    }
}
