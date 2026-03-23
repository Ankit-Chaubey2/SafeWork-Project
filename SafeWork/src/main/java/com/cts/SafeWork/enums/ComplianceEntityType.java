package com.cts.SafeWork.enums;

public enum ComplianceEntityType {
    Hazard, Inspection, Program;

    public enum TrainingStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED,
        FAILED,
        PLANNED
    }
}
