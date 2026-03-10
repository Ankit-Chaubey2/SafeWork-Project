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
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long incidentId;

    private String action;
    private Date incidentDate;
    private String incidentStatus;

    @ManyToOne
    @JoinColumn(name = "hazard_id", referencedColumnName = "hazardId")
    private Hazard hazard;

    @ManyToOne
    @JoinColumn(name = "officer_id", referencedColumnName = "userId")
    private User officer;
}
