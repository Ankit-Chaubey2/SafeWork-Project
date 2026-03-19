package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.projection.HazardReportProjection;
import com.cts.SafeWork.repository.HazardRepository;
import com.cts.SafeWork.service.IHazardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hazard")
public class HazardController {

    @Autowired
    private IHazardService hazardService;

        @PostMapping ("/postHazard/{employeeId}")
    public ResponseEntity<HazardRequestDto> addHazard(@PathVariable Long employeeId , @RequestBody HazardRequestDto request){
        return ResponseEntity.status(200).body(hazardService.addHazard(employeeId, request));
    }
    @GetMapping("/getAllHazard")
    public ResponseEntity<List<HazardReportProjection>> getAllHazards(){ // wrap inside ResponseEntity
        return ResponseEntity.status(200).body(hazardService.getAllHazards());
    }

    @GetMapping("/getById/{hazardId}")
    public ResponseEntity<Hazard> getHazardById(@PathVariable Long hazardId){
        return ResponseEntity.status(200).body(hazardService.getHazardById(hazardId));
    }

    @DeleteMapping("/delete/{hazardId}")
    public ResponseEntity<String> deleteHazard(@PathVariable Long hazardId){
        return ResponseEntity.status(200).body(hazardService.deleteHazard(hazardId));
    }

    @PutMapping("/update/{hazardId}/{employeeId}")
    public ResponseEntity<HazardRequestDto> updateHazard(@PathVariable Long hazardId, @PathVariable Long employeeId, @RequestBody HazardRequestDto hazardRequestDto){
        return ResponseEntity.status(200).body(hazardService.updateHazard(hazardId, employeeId, hazardRequestDto));
    }

}



