package com.cts.SafeWork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
public class HazardRequestDto {

    private long employeeId;
    private long hazardId;
    private String hazardDescription;
    private String hazardLocation;
    private LocalDate hazardDate;
    private String hazardStatus;

}
