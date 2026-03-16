package com.cts.SafeWork.dto;

import com.cts.SafeWork.enums.HazardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
public class HazardRequestDto {

    private String hazardDescription;
    private String hazardLocation;
    private LocalDate hazardDate;
    private HazardStatus hazardStatus;

}
