package com.cts.SafeWork.projection;

import java.util.Date;

public interface IncidentReportProjection {
    long getHazardId();
    long getIncidentId();
    String getAction();
    Date getIncidentDate();
    String getIncidentStatus();
}
