package com.cts.SafeWork.service;

import com.cts.SafeWork.dto.HazardRequestDto;
import com.cts.SafeWork.projection.HazardReportProjection;

import java.util.List;

public interface IHazardService  {
    List<HazardReportProjection> getHazards();
    HazardRequestDto addHazard(HazardRequestDto hazardRequestDto);

}
