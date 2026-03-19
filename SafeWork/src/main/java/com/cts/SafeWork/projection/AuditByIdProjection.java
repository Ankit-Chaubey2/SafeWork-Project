package com.cts.SafeWork.projection;

import com.cts.SafeWork.enums.AuditScope;
import com.cts.SafeWork.enums.AuditStatus;
import java.time.LocalDate;

public interface AuditByIdProjection {
    AuditScope getAuditScope();
    String getAuditFinding();
    LocalDate getAuditDate();
    AuditStatus getAuditStatus();
}
