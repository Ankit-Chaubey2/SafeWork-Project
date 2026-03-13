package com.cts.SafeWork.projection;

import java.util.Date;

public interface HazardReportProjection {
    long getHazardId();
    String getHazardDescription();
    String getHazardLocation();
    Date getHazardDate();
    String getHazardStatus();
}

