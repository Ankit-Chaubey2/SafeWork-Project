package com.cts.SafeWork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inspection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long inspectionId;

    private String inspectionLocation;
    private String inspectionFindings;
    private Date inspectionDate;
    private String inspectionStatus;

    @ManyToOne
    @JoinColumn(name = "officer_id", referencedColumnName = "userId")
    private User officer;

    @OneToMany(mappedBy = "inspection")
    private List<ComplianceCheck> complianceChecks;
}
