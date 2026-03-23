package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.IncidentRequestDto;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.entity.Incident;
import com.cts.SafeWork.entity.User;
import com.cts.SafeWork.enums.HazardStatus;
import com.cts.SafeWork.exception.HazardNotFoundException;
import com.cts.SafeWork.exception.IncidentAlreadyReportedException;
import com.cts.SafeWork.exception.IncidentNotFoundException;
import com.cts.SafeWork.exception.UserNotFoundException;
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

    private final IncidentRepository incidentRepository;
    private final HazardRepository hazardRepository;
    private final UserRepository userRepository;

    @Autowired
    public IncidentServiceImpl(IncidentRepository incidentRepository,HazardRepository hazardRepository,UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.hazardRepository = hazardRepository;
        this.userRepository = userRepository;
    }


    public List<IncidentReportProjection> getIncidents() {
        return incidentRepository.getIncidents();
    }

    public Incident getIncidentById(Long incidentId) {
        return incidentRepository.findById(incidentId).orElseThrow(()-> new IncidentNotFoundException(incidentId));

    }

    @Override
    @Transactional
    public IncidentRequestDto addIncident(Long hazardId, Long userId, IncidentRequestDto incidentRequestDto){

        // 1) Ensure employee exists
        Hazard hazard = hazardRepository.findById(hazardId)
                .orElseThrow(() -> new HazardNotFoundException(hazardId));


        // Only create INCIDENT if the HAZARD status is PENDING
        if (hazard.getHazardStatus() != HazardStatus.PENDING) {
            throw new IncidentAlreadyReportedException();
        }


        // Ensure user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));


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

    public Incident getIncidentByHazardId(Long hazardId){

        return incidentRepository.findById(hazardId).orElseThrow(()-> new IncidentNotFoundException(hazardId));
    }
}
