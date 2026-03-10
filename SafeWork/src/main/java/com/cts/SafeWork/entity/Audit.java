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
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long auditId;

    private String auditScope;
    private String auditFinding;
    private Date auditDate;
    private String auditStatus;

    @ManyToOne
    @JoinColumn(name = "officer_id", referencedColumnName = "userId")
    private User officer;
}
