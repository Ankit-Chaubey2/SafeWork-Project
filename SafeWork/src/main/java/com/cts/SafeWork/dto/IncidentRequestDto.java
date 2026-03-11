package com.cts.SafeWork.dto;

import lombok.Data;

import java.util.Date;

@Data
public class IncidentRequestDto{

    private long hazardId;
    private long incidentId;
    private String action;
    private Date incidentDate;
    private String incidentStatus;
}
