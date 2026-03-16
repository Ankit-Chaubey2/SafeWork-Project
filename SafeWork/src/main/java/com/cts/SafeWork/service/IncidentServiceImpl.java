package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.IncidentRequestDto;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.entity.Incident;
import com.cts.SafeWork.entity.User;
import com.cts.SafeWork.enums.HazardStatus;
import com.cts.SafeWork.projection.IncidentReportProjection;
import com.cts.SafeWork.repository.HazardRepository;
import com.cts.SafeWork.repository.IncidentRepository;
import com.cts.SafeWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncidentServiceImpl implements IIncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private HazardRepository hazardRepository;

    @Autowired
    private UserRepository userRepository;

    public List<IncidentReportProjection> getIncidents() {
        return incidentRepository.getIncidents();
    }

    public Incident getIncidentById(Long incidentId) {
        return incidentRepository.findById(incidentId).orElseThrow(()-> new RuntimeException("incident id not found"));

    }

    @Override
    @Transactional
    public IncidentRequestDto addIncident(Long hazardId, Long userId, IncidentRequestDto incidentRequestDto){

        // 1) Ensure employee exists
        Hazard hazard = hazardRepository.findById(hazardId)
                .orElseThrow(() -> new IllegalArgumentException("Hazard not found with id: " + hazardId));


        // Only create INCIDENT if the HAZARD status is PENDING
        if (hazard.getHazardStatus() != HazardStatus.PENDING) {
            throw new IllegalStateException("Cannot create incident: hazard is not PENDING. Current status = " + hazard.getHazardStatus());
        }


        // Ensure user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));


        Incident incident = new Incident();
        incident.setAction(incidentRequestDto.getAction());
        incident.setIncidentDate(incidentRequestDto.getIncidentDate());


        incident.setHazard(hazard);
        incident.setOfficer(user);

        //The hazard status got updated to "COMPLETED"
        hazard.setHazardStatus(HazardStatus.COMPLETED);
        incidentRepository.save(incident);

        return incidentRequestDto;

    }

}
