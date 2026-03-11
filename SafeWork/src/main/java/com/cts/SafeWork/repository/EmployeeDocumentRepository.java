

package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long> {
    // Custom query to find docs by employee ID
    List<EmployeeDocument> findByEmployee_EmployeeId(long employeeId);
}