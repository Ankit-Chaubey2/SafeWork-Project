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
public class Hazard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long hazardId;

    private String hazardDescription;
    private String hazardLocation;
    private Date hazardDate;
    private String hazardStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    private Employee employee;

    @OneToMany(mappedBy = "hazard")
    private List<Incident> incidents;
}
