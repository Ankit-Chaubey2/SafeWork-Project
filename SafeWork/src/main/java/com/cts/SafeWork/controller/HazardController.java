package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.projection.HazardReportProjection;
import com.cts.SafeWork.repository.HazardRepository;
import com.cts.SafeWork.service.IHazardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hazard")
public class HazardController {

    @Autowired
    private IHazardService hazardService;

    @PostMapping ("/{employeeId}/postHazard")
    public HazardRequestDto addHazard(@PathVariable Long employeeId , @RequestBody HazardRequestDto request){
        return hazardService.addHazard(employeeId, request);
    }
    @GetMapping("/getHazard")
    public List<HazardReportProjection> getHazards(){
        return hazardService.getHazards();
    }

    @GetMapping("/{hazardId}/getById")
    public Hazard getHazardById(@PathVariable Long hazardId){
        return hazardService.getHazardById(hazardId);
    }


}



