package com.cts.SafeWork.entity;

import com.cts.SafeWork.enums.ComplianceEntityType;
import com.cts.SafeWork.enums.ComplianceResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long complianceId;

    private long entityId;   // Generic reference
    @Enumerated(EnumType.STRING)
    private ComplianceEntityType entityType;
    @Enumerated(EnumType.STRING)
    private ComplianceResult complianceResult;
    private LocalDate complianceDate;
    private String complianceNotes;

}
