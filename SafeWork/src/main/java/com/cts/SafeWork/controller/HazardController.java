package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.projection.HazardReportProjection;
import com.cts.SafeWork.service.IHazardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hazard")
public class HazardController {

    @Autowired
    private IHazardService hazardService;

    @PostMapping ("/post")
    public HazardRequestDto addHazard(@RequestBody HazardRequestDto request){
        return hazardService.addHazard(request);
    }
    @GetMapping("/get")
    public List<HazardReportProjection> getHazards(){
        return hazardService.getHazards();
    }

}



