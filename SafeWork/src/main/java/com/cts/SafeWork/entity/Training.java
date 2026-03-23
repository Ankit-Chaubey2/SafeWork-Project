


package com.cts.SafeWork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty; //  Added import for custom getters
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //  Fixed duplicate ID error
    private Long trainingId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate trainingCompletionDate;

    @Enumerated(EnumType.STRING)
    private TrainingStatus trainingStatus;

    @JsonIgnore // Prevent infinite recursion
    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "programId")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    @JsonBackReference("training-employees")
    private Employee employee;

    //  Added custom getter to show programId in JSON
    @JsonProperty("programId")
    public Long getProgramIdForJson() {
        if (this.program != null) {
            return this.program.getProgramId();
        }
        return null;
    }

    //  Added custom getter to show employeeId in JSON
    @JsonProperty("employeeId")
    public Long getEmployeeIdForJson() {
        if (this.employee != null) {
            return this.employee.getEmployeeId();
        }
        return null;
    }
}