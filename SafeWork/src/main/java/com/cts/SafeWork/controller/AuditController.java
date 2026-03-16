package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.Audit;
import com.cts.SafeWork.enums.AuditScope;
import com.cts.SafeWork.enums.AuditStatus;
import com.cts.SafeWork.service.IAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private IAuditService auditService;


    @PostMapping("/createAudit")
    public void createAudit(@RequestBody Audit audit) {
        auditService.createAudit(audit);
    }

    //error
    @GetMapping("/getAllAudits")
    public List<Audit> getAllAudits() {
        return auditService.getAllAudits();
    }

    @GetMapping("/getAuditById/{auditId}")
    public Audit getAuditById(@PathVariable Long auditId) {
        return auditService.getAuditById(auditId).
                orElseThrow(() -> new RuntimeException("Record not found with auditId:" + auditId));
    }


    @PutMapping("updateAudit/{auditId}")
    public Audit updateAudit(@PathVariable Long auditId, @RequestBody Audit updatedAudit) {
        return auditService.updateAudit(auditId, updatedAudit);
    }

    @DeleteMapping("/deleteAudit/{auditId}")
    public void deleteAudit(@PathVariable Long auditId) {
        auditService.deleteAudit(auditId);
    }


    @GetMapping("/findByAuditStatus/{auditStatus}")
    public List<Audit> findByAuditStatus(@PathVariable AuditStatus auditStatus) {
        return auditService.findByAuditStatus(auditStatus);
    }

    @GetMapping("/findByAuditScope/{auditScope}")
    public List<Audit> findByAuditScope(@PathVariable AuditScope auditScope) {
        return auditService.findByAuditScope(auditScope);
    }

    @GetMapping("/findByOfficer_UserId/{userId}")
    public List<Audit> findByOfficer_UserId(@PathVariable Long userId) {
        return auditService.findByOfficer_UserId(userId);
    }


}
