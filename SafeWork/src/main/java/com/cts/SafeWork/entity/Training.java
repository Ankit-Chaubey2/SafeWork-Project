//package com.cts.SafeWork.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Training {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long trainingId;
//
//    private Date trainingCompletionDate;
//    private String trainingStatus;
//
//    @ManyToOne
//    @JoinColumn(name = "program_id", referencedColumnName = "programId")
//    private Program program;
//
//    @ManyToOne
//    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
//    private Employee employee;
//}


//package com.cts.SafeWork.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference; // 1. Ye import add karo
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.util.Date;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Training {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 2. IDENTITY use karo
//    private long trainingId;
//
//    private Date trainingCompletionDate;
//    private String trainingStatus;
//
//    @ManyToOne
//    @JoinColumn(name = "program_id", referencedColumnName = "programId")
//    private Program program;
//
//    @ManyToOne
//    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
//    @JsonBackReference // 3. YE SABSE ZAROORI HAI - Loop todne ke liye
//    private Employee employee;
//}

package com.cts.SafeWork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trainingId;

    private Date trainingCompletionDate;
    private String trainingStatus;

    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "programId")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    @JsonBackReference("training-employees")   // 🔥 FIXED MATCHING VALUE
    private Employee employee;
}
