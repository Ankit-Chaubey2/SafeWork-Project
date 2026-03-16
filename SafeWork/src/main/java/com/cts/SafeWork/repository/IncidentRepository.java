package com.cts.SafeWork.repository;


import com.cts.SafeWork.dto.IncidentRequestDto;
import com.cts.SafeWork.entity.Incident;
import com.cts.SafeWork.projection.HazardReportProjection;
import com.cts.SafeWork.projection.IncidentReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident,Long> {

    @Query("SELECT h.id as hazardId, "+
            "i.incidentId as incidentId, "+
            "i.action as action, "+
            "i.incidentDate as incidentDate "+
            " FROM Incident i JOIN i.hazard h")
    List<IncidentReportProjection> getIncidents();



}
