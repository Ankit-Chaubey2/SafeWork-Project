package com.cts.SafeWork.enums;

public enum AuditScope {
    FULL_SITE, SECTIONAL, INDIVIDUAL_STATION;

    public enum ProgramStatus {
        PLANNED,
        ACTIVE,
        COMPLETED,
        CANCELLED
    }
}
