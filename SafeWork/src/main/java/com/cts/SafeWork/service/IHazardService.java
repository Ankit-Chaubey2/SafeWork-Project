package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.entity.Hazard;
import com.cts.SafeWork.projection.HazardReportProjection;

import java.util.List;

public interface IHazardService  {
    List<HazardReportProjection> getAllHazards();

    HazardRequestDto addHazard(Long employeeId, HazardRequestDto hazardRequestDto);

    Hazard getHazardById(Long hazardId);

    String deleteHazard(Long hazardId);

    HazardRequestDto updateHazard(Long hazardId, Long employeeId, HazardRequestDto hazardRequestDto);

}
