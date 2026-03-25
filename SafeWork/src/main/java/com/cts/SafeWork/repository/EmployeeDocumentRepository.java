package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long> {

    List<EmployeeDocument> findByEmployee_EmployeeId(long employeeId);


    Optional<EmployeeDocument> findByEmployeeDocumentIDAndEmployee_Email(Long docId, String email);
}