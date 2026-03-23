package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.IncidentRequestDto;
import com.cts.SafeWork.entity.Incident;
import com.cts.SafeWork.projection.IncidentReportProjection;

import java.util.List;

public interface IIncidentService {
    List<IncidentReportProjection> getIncidents();

    IncidentRequestDto addIncident(Long hazardId, Long userId, IncidentRequestDto incidentRequestDto);

    Incident getIncidentById(Long  incidentId);

    Incident getIncidentByHazardId(Long hazardId);
}
