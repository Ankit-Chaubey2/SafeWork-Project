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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    private String getLoggedInUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<HazardReportProjection> getAllHazards() {
        log.info("Fetching all hazard report projections.");
        return hazardRepository.getAllHazards();
    }

    @Override
    public Hazard getHazardById(Long hazardId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();

        // Check if the user is a Hazard Officer or Admin
        boolean isOfficerOrAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("HAZARD_OFFICER") || a.getAuthority().equals("ADMIN"));

        Hazard hazard = hazardRepository.findById(hazardId)
                .orElseThrow(() -> new HazardNotFoundException(hazardId));

        // SECURITY CHECK: Allow if it's the owner OR if it's an Officer/Admin
        if (!isOfficerOrAdmin && !hazard.getEmployee().getEmail().equalsIgnoreCase(currentUserEmail)) {
            log.error("Unauthorized Access: {} tried to view Hazard ID {}", currentUserEmail, hazardId);
            throw new InvalidEmployeeException("Access Denied: You do not have permission to view this report.");
        }

        return hazard;
    }

    @Override
    @Transactional
    public HazardRequestDto addHazard(Long employeeID, HazardRequestDto hazardRequestDto) {
        String currentUserEmail = getLoggedInUserEmail();
        log.info("User {} reporting hazard. Checking URL ID: {}", currentUserEmail, employeeID);


        Employee employee = employeeRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new EmployeeNotFoundException("Authenticated employee not found."));


        if (!Objects.equals(employee.getEmployeeId(), employeeID)) {
            log.warn("Impersonation Attempt! Token: {}, URL ID: {}", currentUserEmail, employeeID);
            throw new InvalidEmployeeException("Security Violation: You can only report hazards for your own Employee ID.");
        }

        Hazard hazard = new Hazard();
        hazard.setHazardDate(hazardRequestDto.getHazardDate());
        hazard.setHazardDescription(hazardRequestDto.getHazardDescription());
        hazard.setHazardStatus(HazardStatus.PENDING);
        hazard.setHazardLocation(hazardRequestDto.getHazardLocation());
        hazard.setEmployee(employee); // Crucial: Link to the employee from the TOKEN

        hazardRepository.save(hazard);
        log.info("Hazard successfully recorded for: {}", currentUserEmail);

        hazardRequestDto.setHazardStatus(HazardStatus.PENDING);
        return hazardRequestDto;
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

    @Override
    @Transactional
    public String deleteHazard(Long hazardId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();

        // 1. Check for Privileged Roles (Admin or Hazard Officer)
        boolean isPrivileged = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("HAZARD_OFFICER") || a.getAuthority().equals("ADMIN"));

        // 2. Fetch the Hazard
        Hazard hazard = hazardRepository.findById(hazardId)
                .orElseThrow(() -> new HazardNotFoundException(hazardId));

        // 3. SECURITY CHECK: Allow if user is Privileged OR if user is the Owner
        if (!isPrivileged && !hazard.getEmployee().getEmail().equalsIgnoreCase(currentUserEmail)) {
            log.error("Delete Denied: User {} is not authorized to delete Hazard {}", currentUserEmail, hazardId);
            throw new InvalidEmployeeException("Access Denied: You cannot delete this report.");
        }

        // 4. Business Rule: Prevent deletion if already processed (Optional for Officers)
        if (!isPrivileged && hazard.getHazardStatus() != HazardStatus.PENDING) {
            throw new IncidentAlreadyReportedException("Hazard cannot be deleted as it is already processed.");
        }

        hazardRepository.delete(hazard);
        log.info("Hazard ID {} deleted by {}", hazardId, currentUserEmail);
        return "Hazard deleted successfully!";
    }
}