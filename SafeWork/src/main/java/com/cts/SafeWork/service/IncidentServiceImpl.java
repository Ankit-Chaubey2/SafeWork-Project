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
import lombok.extern.slf4j.Slf4j; // 1. SLF4J Import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j // 2. Add Lombok Logging
public class IncidentServiceImpl implements IIncidentService {

    private final IncidentRepository incidentRepository;
    private final HazardRepository hazardRepository;
    private final UserRepository userRepository;

    @Autowired
    public IncidentServiceImpl(IncidentRepository incidentRepository, HazardRepository hazardRepository, UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.hazardRepository = hazardRepository;
        this.userRepository = userRepository;
    }

    public List<IncidentReportProjection> getIncidents() {
        log.info("Fetching all incident projections from database.");
        return incidentRepository.getIncidents();
    }

    public Incident getIncidentById(Long incidentId) {
        log.info("Searching for incident with ID: {}", incidentId);
        return incidentRepository.findById(incidentId)
                .orElseThrow(() -> {
                    log.error("Incident retrieval failed: ID {} not found", incidentId);
                    return new IncidentNotFoundException(incidentId);
                });
    }

    @Override
    @Transactional
    public IncidentRequestDto addIncident(Long hazardId, Long userId, IncidentRequestDto incidentRequestDto) {
        log.info("Initiating incident creation for Hazard ID: {} by User ID: {}", hazardId, userId);

        // 1) Ensure hazard exists
        Hazard hazard = hazardRepository.findById(hazardId)
                .orElseThrow(() -> {
                    log.error("Hazard ID {} not found during incident creation", hazardId);
                    return new HazardNotFoundException(hazardId);
                });

        // 2) Validation check
        if (hazard.getHazardStatus() != HazardStatus.PENDING) {
            log.warn("Incident creation blocked: Hazard ID {} is already in status {}", hazardId, hazard.getHazardStatus());
            throw new IncidentAlreadyReportedException();
        }

        // 3) Ensure user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User ID {} (Officer) not found", userId);
                    return new UserNotFoundException(userId);
                });

        // 4) Mapping and saving
        Incident incident = new Incident();
        incident.setAction(incidentRequestDto.getAction());
        incident.setIncidentDate(incidentRequestDto.getIncidentDate());
        incident.setHazard(hazard);
        incident.setOfficer(user);

        // Update hazard status
        log.info("Updating Hazard ID: {} status from PENDING to COMPLETED", hazardId);
        hazard.setHazardStatus(HazardStatus.COMPLETED);

        incidentRepository.save(incident);
        log.info("Successfully saved incident record. Action taken: '{}'", incident.getAction());

        return incidentRequestDto;
    }

    public Incident getIncidentByHazardId(Long hazardId) {
        log.info("Searching for incident associated with Hazard ID: {}", hazardId);
        // Note: Check your repository; findById(hazardId) looks for Incident PK,
        // not necessarily the Foreign Key. If this fails, use findByHazard_HazardId.
        return incidentRepository.findById(hazardId)
                .orElseThrow(() -> {
                    log.warn("No incident record found for Hazard ID: {}", hazardId);
                    return new IncidentNotFoundException(hazardId);
                });
    }
}