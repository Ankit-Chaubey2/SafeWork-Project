package com.cts.SafeWork.controller;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.projection.HazardReportProjection;
import com.cts.SafeWork.service.IHazardService;
import lombok.extern.slf4j.Slf4j; // 1. Import SLF4J
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hazard")
@Slf4j // 2. Add Lombok annotation for log object
public class HazardController {

    @Autowired
    private IHazardService hazardService;

    @PostMapping("/postHazard/{employeeId}")
    public ResponseEntity<HazardRequestDto> addHazard(@PathVariable Long employeeId, @RequestBody HazardRequestDto request) {
        log.info("Received request to report a new hazard. Reported by Employee ID: {}", employeeId);
        HazardRequestDto savedHazard = hazardService.addHazard(employeeId, request);
        log.info("Successfully recorded hazard for Employee ID: {}", employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHazard);
    }

    @GetMapping("/getAllHazard")
    public ResponseEntity<List<HazardReportProjection>> getAllHazards() {
        log.info("Fetching all hazard reports from the database.");
        List<HazardReportProjection> hazards = hazardService.getAllHazards();
        log.info("Total hazards retrieved: {}", hazards.size());
        return ResponseEntity.ok(hazards);
    }

    @GetMapping("/getById/{hazardId}")
    public ResponseEntity<Hazard> getHazardById(@PathVariable Long hazardId) {
        log.info("Searching for hazard details with ID: {}", hazardId);
        Hazard hazard = hazardService.getHazardById(hazardId);
        return ResponseEntity.ok(hazard);
    }

    @DeleteMapping("/delete/{hazardId}")
    public ResponseEntity<String> deleteHazard(@PathVariable Long hazardId) {
        log.warn("Attempting to delete hazard record with ID: {}", hazardId);
        String response = hazardService.deleteHazard(hazardId);
        log.info("Hazard ID: {} deleted successfully.", hazardId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{hazardId}/{employeeId}")
    public ResponseEntity<HazardRequestDto> updateHazard(
            @PathVariable Long hazardId,
            @PathVariable Long employeeId,
            @RequestBody HazardRequestDto hazardRequestDto) {

        log.info("Updating hazard ID: {} by Employee ID: {}", hazardId, employeeId);
        HazardRequestDto updated = hazardService.updateHazard(hazardId, employeeId, hazardRequestDto);
        log.info("Hazard ID: {} updated successfully.", hazardId);
        return ResponseEntity.ok(updated);
    }
}