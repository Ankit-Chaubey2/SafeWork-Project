package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.repository.HazardRepository;
import com.cts.SafeWork.repository.IncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)

public class HazardServiceTest {
    @Mock
    private HazardRepository hazardRepository;


    @InjectMocks
    private HazardServiceImpl hazardService;

    private HazardRequestDto hazardRequestDto;

    @BeforeEach
    public void setup() {
        hazardRequestDto = new HazardRequestDto();
        hazardRequestDto.setEmployeeId(1);
        hazardRequestDto.setHazardId(1);
        hazardRequestDto.setHazardDate(LocalDate.now());
        hazardRequestDto.setHazardLocation("Hazard Location");
        hazardRequestDto.setHazardDescription("Hazard Description");
        hazardRequestDto.setHazardStatus("Hazard Status");
    }

    @Test
    public void addHazard_200() {

        hazardService.addHazard(hazardRequestDto);

    }

    @Test
    public void addHazard_409() {

    }
}
