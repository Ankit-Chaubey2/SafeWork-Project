package com.cts.SafeWork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.ToOne;

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
//    private String incidentStatus;

    @OneToOne
    @JoinColumn(name = "hazard_id", referencedColumnName = "hazardId")
    private Hazard hazard;

    @ManyToOne
    @JsonBackReference(value = "officer-incident")
    @JoinColumn(name = "officer_id", referencedColumnName = "userId")
    private User officer;


}
