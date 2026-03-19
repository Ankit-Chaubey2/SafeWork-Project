package com.cts.SafeWork.entity;

import com.cts.SafeWork.enums.AuditScope;
import com.cts.SafeWork.enums.AuditStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;


    @NotNull(message = "Audit scope is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditScope auditScope;


    @NotBlank(message = "Audit finding cannot be blank")
    @Column(nullable = false, length = 255)
    private String auditFinding;


    @NotNull(message = "Audit date is mandatory")
    @PastOrPresent(message = "Audit date cannot be in the future")
    @Column(nullable = false)
    private LocalDate auditDate;


    @NotNull(message = "Audit status is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditStatus auditStatus;


    @NotNull(message = "Officer is mandatory")
    @ManyToOne
    @JoinColumn(
            name = "officer_id",
            referencedColumnName = "userId",
            nullable = false
    )
    @JsonBackReference
    private User officer;
}
