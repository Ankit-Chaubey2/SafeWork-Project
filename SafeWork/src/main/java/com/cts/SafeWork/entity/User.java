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
    private String userRole;
    private String userEmail;
    private String userContact;
    private String userStatus;

    @OneToMany(mappedBy = "user")
    private List<UserAuditLog> userAuditLogs;

    @OneToMany(mappedBy = "officer")
    private List<Inspection> inspections;

    @OneToMany(mappedBy = "officer")
    private List<Audit> audits;

    @OneToMany(mappedBy = "officer")
    private List<Incident> incidents;
}
