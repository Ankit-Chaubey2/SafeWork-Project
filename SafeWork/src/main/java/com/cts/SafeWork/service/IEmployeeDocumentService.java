


package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.EmployeeDocument;
import java.util.List;

public interface IEmployeeDocumentService {
    EmployeeDocument uploadDocument(EmployeeDocument document);
    List<EmployeeDocument> getDocumentsByEmployee(long employeeId);
}