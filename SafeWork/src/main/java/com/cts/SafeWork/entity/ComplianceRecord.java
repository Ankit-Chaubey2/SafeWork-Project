package com.cts.SafeWork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long complianceId;

    private long entityId;   // Generic reference
    private String entityType;
    private String complianceResult;
    private Date complianceDate;
    private String complianceNotes;
}
