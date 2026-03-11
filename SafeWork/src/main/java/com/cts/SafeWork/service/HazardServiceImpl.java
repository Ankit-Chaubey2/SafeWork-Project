package com.cts.SafeWork.service;
import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.projection.HazardReportProjection;
import com.cts.SafeWork.repository.HazardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HazardServiceImpl implements IHazardService {

    @Autowired
    private HazardRepository hazardRepository  ;

    public List<HazardReportProjection> getHazards(){
        return hazardRepository.getHazards();
    }

    public HazardRequestDto addHazard(HazardRequestDto hazardRequestDto) {
        Employee employee = new Employee();
        employee.setEmployeeId(hazardRequestDto.getEmployeeId());

        Hazard hazard = new Hazard();
        hazard.setHazardId(hazardRequestDto.getHazardId());
        hazard.setHazardDate(hazardRequestDto.getHazardDate());
        hazard.setHazardDescription(hazardRequestDto.getHazardDescription());
        hazard.setHazardStatus(hazardRequestDto.getHazardStatus());
        hazard.setHazardLocation(hazardRequestDto.getHazardLocation());

        hazard.setEmployee(employee);
        hazardRepository.save(hazard);
        return hazardRequestDto;

    }
}
