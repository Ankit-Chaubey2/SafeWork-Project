

package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Inspection;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


public interface IInspectionService {
    // Primary method for creating/scheduling an inspection
    Inspection createInspection(Inspection inspection);

    List<Inspection> getAllInspections();

    Optional<Inspection> getInspectionById(long inspectionId);

    Inspection updateInspection(long inspectionId, Inspection inspectionDetails);

    boolean deleteInspection(long inspectionId);

    Inspection updateInspectionStatus(long id, String status);
}