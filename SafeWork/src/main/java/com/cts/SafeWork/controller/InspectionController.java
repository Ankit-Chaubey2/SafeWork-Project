package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.Inspection;
import com.cts.SafeWork.service.IInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for Module 4.4: Safety Inspection & Compliance Tracking.
 * Handles workplace inspections and findings.
 */
@RestController
@RequestMapping("/api/inspections")
public class InspectionController {

    @Autowired
    private IInspectionService inspectionService;

    /**
     * Creates a new inspection record.
     * Used by Safety Officers to log workplace visits.
     */
    @PostMapping
    public ResponseEntity<Inspection> createInspection(@RequestBody Inspection inspection) {
        Inspection createdInspection = inspectionService.createInspection(inspection);
        return new ResponseEntity<>(createdInspection, HttpStatus.CREATED);
    }

    /**
     * Retrieves all inspections.
     * Accessible by Managers and Auditors for performance monitoring[cite: 10, 13].
     */
    @GetMapping
    public ResponseEntity<List<Inspection>> getAllInspections() {
        List<Inspection> inspections = inspectionService.getAllInspections();
        return new ResponseEntity<>(inspections, HttpStatus.OK);
    }

    /**
     * Retrieves a specific inspection by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<? extends Object> getInspectionById(@PathVariable long id) {
        Optional<Inspection> inspection = inspectionService.getInspectionById(id);
        return inspection != null ?
                new ResponseEntity<>(inspection, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Updates the status of an inspection (e.g., Scheduled, In-Progress, Completed).
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Inspection> updateStatus(@PathVariable long id, @RequestParam String status) {
        Inspection updated = inspectionService.updateInspectionStatus(id, status);
        return updated != null ?
                new ResponseEntity<>(updated, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}