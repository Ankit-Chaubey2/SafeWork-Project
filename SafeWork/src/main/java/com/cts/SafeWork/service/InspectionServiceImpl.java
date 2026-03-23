package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Inspection;
import com.cts.SafeWork.repository.InspectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InspectionServiceImpl implements IInspectionService {

    @Autowired
    private InspectionRepository inspectionRepository;

    @Override
    public Inspection createInspection(Inspection inspection) {
        log.info("Initiating creation of new inspection for location: {}", inspection.getInspectionLocation());
        Inspection savedInspection = inspectionRepository.save(inspection);
        log.info("Inspection successfully saved with ID: {}", savedInspection.getInspectionId());
        return savedInspection;
    }

    @Override
    public List<Inspection> getAllInspections() {
        log.info("Retrieving all inspection records from the database");
        return inspectionRepository.findAll();
    }

    @Override
    public Optional<Inspection> getInspectionById(long inspectionId) {
        log.info("Searching for inspection with ID: {}", inspectionId);
        return inspectionRepository.findById(inspectionId);
    }

    @Override
    public Inspection updateInspection(long inspectionId, Inspection inspectionDetails) {
        log.info("Attempting to update inspection ID: {}", inspectionId);

        Inspection inspection = inspectionRepository.findById(inspectionId)
                .orElseThrow(() -> {
                    log.error("Update failed: Inspection ID {} not found", inspectionId);
                    return new RuntimeException("Inspection not found with id: " + inspectionId);
                });

        log.debug("Syncing fields for inspection ID: {}", inspectionId);
        inspection.setInspectionLocation(inspectionDetails.getInspectionLocation());
        inspection.setInspectionFindings(inspectionDetails.getInspectionFindings());
        inspection.setInspectionStatus(inspectionDetails.getInspectionStatus());
        inspection.setInspectionDate(inspectionDetails.getInspectionDate());

        Inspection updated = inspectionRepository.save(inspection);
        log.info("Inspection ID: {} updated successfully", inspectionId);
        return updated;
    }

    @Override
    public boolean deleteInspection(long id) {
        log.warn("Request to delete inspection ID: {}", id);
        if (inspectionRepository.existsById(id)) {
            inspectionRepository.deleteById(id);
            log.info("Inspection ID: {} has been deleted", id);
            return true;
        }
        log.error("Delete failed: ID {} not found in database", id);
        return false;
    }

    @Override
    public Inspection updateInspectionStatus(long id, String status) {
        log.info("Updating status to '{}' for inspection ID: {}", status, id);

        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Status update failed: Inspection ID {} not found", id);
                    return new RuntimeException("Inspection not found with id: " + id);
                });

        inspection.setInspectionStatus(status);
        Inspection updated = inspectionRepository.save(inspection);
        log.info("Status for ID: {} successfully updated to {}", id, status);

        return updated;
    }
}