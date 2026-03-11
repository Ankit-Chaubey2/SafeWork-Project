package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.dto.IncidentRequestDto;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.entity.Incident;
import com.cts.SafeWork.projection.IncidentReportProjection;
import com.cts.SafeWork.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class IncidentServiceImpl implements IIncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public List<IncidentReportProjection> getIncidents() {
        return incidentRepository.getIncidents();
    }

    public IncidentRequestDto addIncident(IncidentRequestDto incidentRequestDto){
        Hazard hazard = new Hazard();
        hazard.setHazardId(incidentRequestDto.getHazardId());

        Incident incident = new Incident();
        incident.setIncidentId(incidentRequestDto.getIncidentId());
        incident.setAction(incidentRequestDto.getAction());
        incident.setIncidentDate(incidentRequestDto.getIncidentDate());
        incident.setIncidentStatus(incidentRequestDto.getIncidentStatus());

        incident.setHazard(hazard);
        incidentRepository.save(incident);
        return incidentRequestDto;

    }

}
