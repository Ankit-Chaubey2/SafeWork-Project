package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.enums.HazardStatus;
import com.cts.SafeWork.projection.HazardReportProjection;
import com.cts.SafeWork.repository.EmployeeRepository;
import com.cts.SafeWork.repository.HazardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HazardServiceImpl implements IHazardService {

    @Autowired
    private HazardRepository hazardRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<HazardReportProjection> getHazards() {
        return hazardRepository.getHazards();
    }

    @Override
    public Hazard getHazardById(Long hazardId) {
        return hazardRepository.findById(hazardId).orElseThrow(()-> new RuntimeException("hazard id not found"));
    }

    @Override
    @Transactional
    public HazardRequestDto addHazard(Long employeeID, HazardRequestDto hazardRequestDto) {
        // 1) Ensure employee exists
        Employee employee = employeeRepository.findById(employeeID)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + employeeID));


        // 2) Map DTO -> Entity (do not set hazardId it's auto-generated)
        Hazard hazard = new Hazard();
        hazard.setHazardDate(hazardRequestDto.getHazardDate());          // assuming Hazard.hazardDate is LocalDate
        hazard.setHazardDescription(hazardRequestDto.getHazardDescription());
        hazard.setHazardStatus(HazardStatus.PENDING);
        hazard.setHazardLocation(hazardRequestDto.getHazardLocation());

        // 3) Set FK via relationship
        hazard.setEmployee(employee);

        // 4) Persist
        hazardRepository.save(hazard);
        hazardRequestDto.setHazardStatus(HazardStatus.PENDING);

        // 5) Return the same request DTO (or you can switch to a Response DTO with generated IDs)
        return hazardRequestDto;
    }
}

