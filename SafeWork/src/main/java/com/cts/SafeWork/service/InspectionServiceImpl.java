package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Inspection;
import com.cts.SafeWork.repository.InspectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InspectionServiceImpl implements IInspectionService {

    @Autowired
    private InspectionRepository inspectionRepository;

    @Override
    public Inspection createInspection(Inspection inspection) {
        // Registers a new inspection in the system
        return inspectionRepository.save(inspection);
    }

    @Override
    public List<Inspection> getAllInspections() {
        // Returns all logs for monitoring by government auditors and managers
        return inspectionRepository.findAll();
    }

    @Override
    public Optional<Inspection> getInspectionById(long inspectionId) {
        return inspectionRepository.findById(inspectionId);
    }

    @Override
    public Inspection updateInspection(long inspectionId, Inspection inspectionDetails) {
        Inspection inspection = inspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new RuntimeException("Inspection not found with id: " + inspectionId));

        // Syncing entity fields with updated details
        inspection.setInspectionLocation(inspectionDetails.getInspectionLocation());
        inspection.setInspectionFindings(inspectionDetails.getInspectionFindings());
        inspection.setInspectionStatus(inspectionDetails.getInspectionStatus());
        inspection.setInspectionDate(inspectionDetails.getInspectionDate());

        return inspectionRepository.save(inspection);
    }

    @Override
    public void deleteInspection(long inspectionId) {
        inspectionRepository.deleteById(inspectionId);
    }

    @Override
    public Inspection updateInspectionStatus(long id, String status) {
        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inspection not found with id: " + id));

        inspection.setInspectionStatus(status);

        return inspectionRepository.save(inspection);
    }
}