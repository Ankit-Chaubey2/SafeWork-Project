package com.cts.SafeWork.dto;

import lombok.Data;

import java.util.Date;

@Data
public class IncidentRequestDto{

    private String action;
    private Date incidentDate;

}
