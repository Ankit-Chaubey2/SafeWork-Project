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
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long programId;

    private String programTitle;
    private String programDescription;
    private Date programStartDate;
    private Date programEndDate;
    private String programStatus;

    @OneToMany(mappedBy = "program")
    private List<Training> trainings;
}
