package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.IncidentRequestDto;
import com.cts.SafeWork.projection.IncidentReportProjection;
import com.cts.SafeWork.repository.IncidentRepository;
import com.cts.SafeWork.service.IIncidentService;
import com.cts.SafeWork.service.IncidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incident")
public class IncidentController {

    @Autowired
    private IIncidentService incidentService;

    @PostMapping("/post")
    public IncidentRequestDto addIncident(@RequestBody IncidentRequestDto request){
        return incidentService.addIncident(request);
    }


    @GetMapping("/get")
    public List<IncidentReportProjection> getIncidents(){
        return incidentService.getIncidents();
    }


}
