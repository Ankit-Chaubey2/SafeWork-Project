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
public class ComplianceCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long checkId;

    private String complianceCheckResult;
    private String complianceCheckNotes;
    private Date complianceCheckDate;
    private String complianceCheckStatus;

    @ManyToOne
    @JoinColumn(name = "inspection_id", referencedColumnName = "inspectionId")
    private Inspection inspection;
}
