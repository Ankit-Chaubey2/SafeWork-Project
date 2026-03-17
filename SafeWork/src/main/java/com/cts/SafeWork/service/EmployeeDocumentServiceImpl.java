//package com.cts.SafeWork.service;
//
//import com.cts.SafeWork.entity.EmployeeDocument;
//import com.cts.SafeWork.repository.EmployeeDocumentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class EmployeeDocumentServiceImpl implements IEmployeeDocumentService {
//
//    @Autowired
//    private EmployeeDocumentRepository documentRepository;
//
//    @Override
//    public EmployeeDocument uploadDocument(EmployeeDocument document) {
//        return documentRepository.save(document);
//    }
//
//    @Override
//    public List<EmployeeDocument> getDocumentsByEmployee(long employeeId) {
//        return documentRepository.findByEmployee_EmployeeId(employeeId);
//    }
//}

package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.EmployeeDocument;
import com.cts.SafeWork.exception.DocumentNotFoundException; // Naya Import
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
        List<EmployeeDocument> docs = documentRepository.findByEmployee_EmployeeId(employeeId);

        // Agar us employee ka ek bhi document nahi mila
        if (docs.isEmpty()) {
            throw new DocumentNotFoundException("Bhai, is Employee (ID: " + employeeId + ") ka koi document nahi mila!");
        }
        return docs;
    }

    // Ek naya method jo single document dhoonde (Download/Delete ke liye kaam aayega)
    public EmployeeDocument getDocumentById(Long docId) {
        return documentRepository.findById(docId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with ID: " + docId));
    }
}