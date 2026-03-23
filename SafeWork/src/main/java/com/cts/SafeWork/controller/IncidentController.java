package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.IncidentRequestDto;
import com.cts.SafeWork.entity.Incident;
import com.cts.SafeWork.projection.IncidentReportProjection;
import com.cts.SafeWork.service.IIncidentService;
import lombok.extern.slf4j.Slf4j; // SLF4J Import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incident")
@Slf4j // Lombok annotation for logging
public class IncidentController {

    @Autowired
    private IIncidentService incidentService;

    @PostMapping("/post/{hazardId}/{userId}")
    public ResponseEntity<IncidentRequestDto> addIncident(
            @PathVariable Long hazardId,
            @PathVariable Long userId,
            @RequestBody IncidentRequestDto request) {

        log.info("REST request to report incident. Hazard ID: {}, User ID: {}", hazardId, userId);
        IncidentRequestDto savedIncident = incidentService.addIncident(hazardId, userId, request);
        log.info("Incident reported successfully for Hazard ID: {} by User ID: {}", hazardId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedIncident);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<IncidentReportProjection>> getIncidents() {
        log.info("REST request to fetch all reported incidents");
        List<IncidentReportProjection> incidents = incidentService.getIncidents();
        log.info("Successfully retrieved {} incident records", incidents.size());

        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/getById/{incidentId}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long incidentId) {
        log.info("REST request to get details for Incident ID: {}", incidentId);
        Incident incident = incidentService.getIncidentById(incidentId);
        return ResponseEntity.ok(incident);
    }

    @GetMapping("/getByHazard/{hazardId}")
    public ResponseEntity<Incident> getIncidentByHazardId(@PathVariable Long hazardId) {
        log.info("REST request to find incident linked to Hazard ID: {}", hazardId);
        Incident incident = incidentService.getIncidentByHazardId(hazardId);
        return ResponseEntity.ok(incident);
    }
}