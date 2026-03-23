package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.enums.HazardStatus;
import com.cts.SafeWork.exception.EmployeeNotFoundException;
import com.cts.SafeWork.exception.HazardNotFoundException;
import com.cts.SafeWork.exception.IncidentAlreadyReportedException;
import com.cts.SafeWork.exception.InvalidEmployeeException;
import com.cts.SafeWork.projection.HazardReportProjection;
import com.cts.SafeWork.repository.EmployeeRepository;
import com.cts.SafeWork.repository.HazardRepository;
import lombok.extern.slf4j.Slf4j; // SLF4J Import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class HazardServiceImpl implements IHazardService {

    private final HazardRepository hazardRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public HazardServiceImpl(HazardRepository hazardRepository, EmployeeRepository employeeRepository) {
        this.hazardRepository = hazardRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<HazardReportProjection> getAllHazards() {
        log.info("Fetching all hazard report projections.");
        return hazardRepository.getAllHazards();
    }

    @Override
    public Hazard getHazardById(Long hazardId) {
        log.info("Searching for hazard with ID: {}", hazardId);
        return hazardRepository.findById(hazardId)
                .orElseThrow(() -> {
                    log.error("Hazard retrieval failed: ID {} not found", hazardId);
                    return new HazardNotFoundException(hazardId);
                });
    }

    @Override
    @Transactional
    public HazardRequestDto addHazard(Long employeeID, HazardRequestDto hazardRequestDto) {
        log.info("Reporting new hazard for Employee ID: {}", employeeID);

        // 1) Ensure employee exists
        Employee employee = employeeRepository.findById(employeeID)
                .orElseThrow(() -> {
                    log.error("Failed to add hazard: Employee ID {} not found", employeeID);
                    return new EmployeeNotFoundException(employeeID);
                });

        // 2) Map DTO -> Entity
        Hazard hazard = new Hazard();
        hazard.setHazardDate(hazardRequestDto.getHazardDate());
        hazard.setHazardDescription(hazardRequestDto.getHazardDescription());
        hazard.setHazardStatus(HazardStatus.PENDING);
        hazard.setHazardLocation(hazardRequestDto.getHazardLocation());
        hazard.setEmployee(employee);

        // 3) Persist
        Hazard savedHazard = hazardRepository.save(hazard);
        log.info("New hazard successfully recorded with status PENDING at location: {}", hazard.getHazardLocation());

        hazardRequestDto.setHazardStatus(HazardStatus.PENDING);
        return hazardRequestDto;
    }

    @Override
    @Transactional
    public String deleteHazard(Long hazardId) {
        log.info("Request received to delete Hazard ID: {}", hazardId);

        Hazard hazard = hazardRepository.findById(hazardId)
                .orElseThrow(() -> {
                    log.error("Deletion failed: Hazard ID {} not found", hazardId);
                    return new HazardNotFoundException(hazardId);
                });

        // Business Rule Validation
        if (hazard.getHazardStatus() != HazardStatus.PENDING) {
            log.warn("Unauthorized deletion attempt: Hazard ID {} is already COMPLETED/REPORTED.", hazardId);
            throw new IncidentAlreadyReportedException("Hazard cannot be deleted because Incident has already been Reported");
        }

        hazardRepository.delete(hazard);
        log.info("Hazard ID: {} deleted successfully from the system.", hazardId);
        return "Hazard deleted successfully!";
    }

    @Override
    @Transactional
    public HazardRequestDto updateHazard(Long hazardId, Long employeeId, HazardRequestDto hazardRequestDto) {
        log.info("Processing update request for Hazard ID: {} by Employee ID: {}", hazardId, employeeId);

        // 1. Fetch the existing Hazard
        Hazard hazard = hazardRepository.findById(hazardId)
                .orElseThrow(() -> {
                    log.error("Update failed: Hazard ID {} not found in database.", hazardId);
                    return new HazardNotFoundException(hazardId);
                });

        // 2. Business Rule: Check if the Hazard is still in PENDING status
        if (hazard.getHazardStatus() != HazardStatus.PENDING) {
            log.warn("Update blocked: Hazard ID {} is already {} and cannot be modified.",
                    hazardId, hazard.getHazardStatus());
            throw new IncidentAlreadyReportedException("Hazard cannot be updated because an Incident has already been reported.");
        }

        // 3. Business Rule: Ownership Check (The "Proper" way to compare IDs)
        // Using Objects.equals handles both Long objects and primitive long values safely
        if (!Objects.equals(hazard.getEmployee().getEmployeeId(), employeeId)) {
            log.error("SECURITY ALERT: Unauthorized update attempt. Employee {} tried to modify Hazard {} owned by Employee {}",
                    employeeId, hazardId, hazard.getEmployee().getEmployeeId());

            throw new InvalidEmployeeException("Permission Denied: You are not the original reporter of this hazard.");
        }

        // 4. Map the updated fields
        log.debug("Mapping new values to Hazard ID: {}", hazardId);
        hazard.setHazardDate(hazardRequestDto.getHazardDate());
        hazard.setHazardDescription(hazardRequestDto.getHazardDescription());
        hazard.setHazardLocation(hazardRequestDto.getHazardLocation());

        // 5. Persist the changes
        hazardRepository.save(hazard);

        log.info("SUCCESS: Hazard ID {} successfully updated by Employee ID {}", hazardId, employeeId);

        return hazardRequestDto;
    }
}