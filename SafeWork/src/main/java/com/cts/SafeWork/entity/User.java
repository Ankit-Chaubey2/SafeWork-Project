package com.cts.SafeWork.entity;

import com.cts.SafeWork.enums.UserRole;
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
    private Long userId;

    private String userName;
    private String userEmail;
    private String userContact;
    private String userStatus;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;



    @OneToMany(mappedBy = "officer", fetch = FetchType.LAZY)
    private List<Incident> incidents;

    @OneToMany(mappedBy = "officer", fetch = FetchType.LAZY)
    private List<Audit> audits;

    @OneToMany(mappedBy = "officer", fetch = FetchType.LAZY)
    private List<Inspection> inspections;


}
