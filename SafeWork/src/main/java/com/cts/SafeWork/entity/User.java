package com.cts.SafeWork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    private String userName;

    private String userEmail;
    private String userContact;
    private String userStatus;

    private String password; // Hashed password

    @Enumerated(EnumType.STRING)
    private UserRole userRole; // Role field

    public enum UserRole {
        ADMIN,
        COMPLIANCE_OFFICER,
        SAFETY_OFFICER,
        EMPLOYEE
    }

    @OneToMany(mappedBy = "user")
    private List<UserAuditLog> userAuditLogs;

    @OneToMany(mappedBy = "officer")
    private List<Inspection> inspections;

    @OneToMany(mappedBy = "officer")
    private List<Audit> audits;

    @OneToMany(mappedBy = "officer")
    private List<Incident> incidents;
}
