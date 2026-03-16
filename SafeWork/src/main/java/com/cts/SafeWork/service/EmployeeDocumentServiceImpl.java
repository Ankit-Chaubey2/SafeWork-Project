package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.EmployeeDocument;
import com.cts.SafeWork.repository.EmployeeDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeDocumentServiceImpl implements IEmployeeDocumentService {

    @Autowired
    private EmployeeDocumentRepository documentRepository;

    @Override
    public EmployeeDocument uploadDocument(EmployeeDocument document) {
        return documentRepository.save(document);
    }

    @Override
    public List<EmployeeDocument> getDocumentsByEmployee(long employeeId) {
        return documentRepository.findByEmployee_EmployeeId(employeeId);
    }
}