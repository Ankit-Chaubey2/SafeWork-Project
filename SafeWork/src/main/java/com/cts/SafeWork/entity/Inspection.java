package com.cts.SafeWork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Inspection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inspectionId;

    private String inspectionLocation;
    private String inspectionFindings;
    private Date inspectionDate;
    private String inspectionStatus;

    @ManyToOne
    @JoinColumn(name = "officer_id", referencedColumnName = "userId")
    @JsonBackReference
    private User officer;

    @JsonIgnore
    @OneToMany(mappedBy = "inspection")
    private List<ComplianceCheck> complianceChecks;
}
