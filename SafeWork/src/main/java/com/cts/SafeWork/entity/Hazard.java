
package com.cts.SafeWork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference; // Ye import zaroori hai
import com.cts.SafeWork.enums.HazardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hazard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO ko IDENTITY karo mismatch se bachne ke liye
    private long hazardId;

    private String hazardDescription;
    private String hazardLocation;
    private LocalDate hazardDate;

    @Enumerated(EnumType.STRING)
    private HazardStatus hazardStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    @JsonBackReference(value = "hazard-employees")
    private Employee employee;

//  @OneToMany(mappedBy = "hazard")
    @OneToOne(mappedBy = "hazard")
    @JsonBackReference(value = "hazard-incident")
    private Incident incidents;
}
