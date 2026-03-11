package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.Hazard;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.SafeWork.projection.HazardReportProjection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HazardRepository extends JpaRepository<Hazard,Long> {

    @Query("SELECT e.id as employeeId, "+
                        "h.hazardId as hazardId, "+
                        "h.hazardDescription as hazardDescription, "+
                        "h.hazardLocation as hazardLocation, "+
                        "h.hazardDate as hazardDate, "+
                        "h.hazardStatus as hazardStatus "+
            " FROM Hazard h JOIN h.employee e")
     List<HazardReportProjection> getHazards();
}
