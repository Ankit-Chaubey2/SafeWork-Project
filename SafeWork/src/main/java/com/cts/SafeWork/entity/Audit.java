package com.cts.SafeWork.entity;

import com.cts.SafeWork.enums.AuditScope;
import com.cts.SafeWork.enums.AuditStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditId;
    @Enumerated(EnumType.STRING)
    private AuditScope auditScope;
    private String auditFinding;
    private LocalDate auditDate;
    @Enumerated(EnumType.STRING)
    private AuditStatus auditStatus;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "officer_id", referencedColumnName = "userId")
    private User officer;
}
