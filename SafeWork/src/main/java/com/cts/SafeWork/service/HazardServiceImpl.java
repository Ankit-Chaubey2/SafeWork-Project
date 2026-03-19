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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HazardServiceImpl implements IHazardService {

    private final HazardRepository hazardRepository;
    private final EmployeeRepository employeeRepository;

    HazardServiceImpl(HazardRepository hazardRepository, EmployeeRepository employeeRepository) {
        this.hazardRepository=hazardRepository;
        this.employeeRepository=employeeRepository;
    }

    @Override
    public List<HazardReportProjection> getAllHazards() {
        return hazardRepository.getAllHazards();
    }

    @Override
    public Hazard getHazardById(Long hazardId) {
        return hazardRepository.findById(hazardId).orElseThrow(()-> new HazardNotFoundException(hazardId));
    }

    @Override
    @Transactional
    public HazardRequestDto addHazard(Long employeeID, HazardRequestDto hazardRequestDto) {
        // 1) Ensure employee exists
        Employee employee = employeeRepository.findById(employeeID)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeID));


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

    @Override
    @Transactional
    public String deleteHazard(Long hazardId) {
        Hazard hazard = hazardRepository.findById(hazardId).orElseThrow(()-> new HazardNotFoundException(hazardId));

        if(hazard.getHazardStatus()!=HazardStatus.PENDING){
            throw new IncidentAlreadyReportedException("Hazard cannot be deleted because Incident has already been Reported");
        }
        hazardRepository.delete(hazard);
        return "Hazard deleted successfully!";
    }

    @Override
    @Transactional
    public HazardRequestDto updateHazard(Long hazardId, Long employeeId, HazardRequestDto hazardRequestDto){
        Hazard hazard = hazardRepository.findById(hazardId).orElseThrow(()-> new HazardNotFoundException(hazardId));
        if(hazard.getHazardStatus()!=HazardStatus.PENDING){
            throw new IncidentAlreadyReportedException("Hazard cannot be updated because Incident has already been Reported");
        }

        if(hazard.getEmployee().getEmployeeId()!=employeeId){
            throw new InvalidEmployeeException();
        }
        hazard.setHazardDate(hazardRequestDto.getHazardDate());
        hazard.setHazardDescription(hazardRequestDto.getHazardDescription());
        hazard.setHazardLocation(hazardRequestDto.getHazardLocation());

        hazardRepository.save(hazard);

        return hazardRequestDto;
    }
}

