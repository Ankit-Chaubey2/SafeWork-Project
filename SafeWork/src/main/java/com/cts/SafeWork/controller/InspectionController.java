package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.Inspection;
import com.cts.SafeWork.service.IInspectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/inspections")
public class InspectionController {

    @Autowired
    private IInspectionService inspectionService;


    @PostMapping("createInspection")
    public ResponseEntity<Inspection> createInspection(@RequestBody Inspection inspection) {
        log.info("Request to create a new inspection: {}", inspection);
        Inspection createdInspection = inspectionService.createInspection(inspection);
        log.info("Inspection created successfully with ID: {}", createdInspection.getInspectionId());
        return new ResponseEntity<>(createdInspection, HttpStatus.CREATED);
    }


    @GetMapping("/getAllInspections")
    public ResponseEntity<List<Inspection>> getAllInspections() {
        log.info("Fetching all inspections from the database");
        List<Inspection> inspections = inspectionService.getAllInspections();
        log.info("Total inspections retrieved: {}", inspections.size());
        return new ResponseEntity<>(inspections, HttpStatus.OK);
    }


    @GetMapping("getInspectionById/{id}")
    public ResponseEntity<? extends Object> getInspectionById(@PathVariable long id) {
        log.info("Fetching inspection details for ID: {}", id);
        Optional<Inspection> inspection = inspectionService.getInspectionById(id);

        if (inspection != null && inspection.isPresent()) {
            log.info("Inspection found for ID: {}", id);
            return new ResponseEntity<>(inspection, HttpStatus.OK);
        } else {
            log.error("Inspection NOT found for ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<Inspection> updateStatus(@PathVariable long id, @RequestParam String status) {
        log.info("Updating status for Inspection ID: {} to {}", id, status);
        Inspection updated = inspectionService.updateInspectionStatus(id, status);

        if (updated != null) {
            log.info("Status updated successfully for ID: {}", id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            log.error("Failed to update status. Inspection ID: {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteInspection/{id}")
    public ResponseEntity<String> deleteInspection(@PathVariable long id) {
        log.warn("Request received to delete Inspection ID: {}", id);
        boolean isDeleted = inspectionService.deleteInspection(id);

        if (isDeleted) {
            log.info("Inspection ID: {} deleted successfully", id);
            return new ResponseEntity<>("Inspection deleted successfully", HttpStatus.OK);
        } else {
            log.error("Deletion failed. Inspection ID: {} not found", id);
            return new ResponseEntity<>("Inspection not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}