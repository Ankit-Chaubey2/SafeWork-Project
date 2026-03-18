package com.cts.SafeWork.exception;

import com.cts.SafeWork.enums.HazardStatus;

public class IncidentAlreadyReportedException extends RuntimeException {
    public IncidentAlreadyReportedException() {
        super("Cannot create incident: hazard is not PENDING.");
    }

    public IncidentAlreadyReportedException(String message){
        super(message);
    }
}
