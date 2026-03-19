package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.IncidentRequestDto;
import com.cts.SafeWork.entity.Incident;
import com.cts.SafeWork.projection.IncidentReportProjection;
import com.cts.SafeWork.repository.IncidentRepository;
import com.cts.SafeWork.service.IIncidentService;
import com.cts.SafeWork.service.IncidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incident")
public class IncidentController {

    @Autowired
    private IIncidentService incidentService;

    @PostMapping("/post/{hazardId}/{userId}")
    public ResponseEntity<IncidentRequestDto> addIncident(@PathVariable Long hazardId, @PathVariable Long userId, @RequestBody IncidentRequestDto request){
        return ResponseEntity.status(200).body(incidentService.addIncident(hazardId, userId, request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<IncidentReportProjection>> getIncidents(){
        return ResponseEntity.status(200).body(incidentService.getIncidents());
    }

    @GetMapping("/getById/{incidentId}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long incidentId){
        return ResponseEntity.status(200).body(incidentService.getIncidentById(incidentId));
    }

    @GetMapping("/getByHazard/{hazardId}")
    public ResponseEntity<Incident> getIncidentByHazardId(@PathVariable Long hazardId){
        return ResponseEntity.status(200).body(incidentService.getIncidentByHazardId(hazardId));
    }

}
