package com.cts.SafeWork.controller;
import com.cts.SafeWork.dto.EmployeeResponseDTO;
import com.cts.SafeWork.entity.Program;

import com.cts.SafeWork.entity.Training;

import com.cts.SafeWork.entity.Employee;

import com.cts.SafeWork.service.IEmployeeService;

import com.cts.SafeWork.service.IProgramService;

import com.cts.SafeWork.service.ITrainingService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;

@RestController

@RequestMapping("/trainings")

@Slf4j

public class TrainingController {

    private final ITrainingService service;

    private final IProgramService programService;

    private final IEmployeeService employeeService;

    @Autowired

    public TrainingController(ITrainingService service, IProgramService programService, IEmployeeService employeeService) {

        this.service = service;

        this.programService = programService;

        this.employeeService = employeeService;

    }

    @GetMapping("/getalltrainings")

    public ResponseEntity<List<Training>> getAllTrainings() {

        log.info("REST request to fetch all training records");

        List<Training> trainings = service.getAllTrainings();

        log.info("Total trainings found: {}", trainings.size());

        return ResponseEntity.ok(trainings);

    }

    @GetMapping("/gettrainnigbyid/{id}")

    public ResponseEntity<Training> getTraining(@PathVariable Long id) {

        log.info("REST request to get training by ID: {}", id);

        return ResponseEntity.ok(service.getTrainingById(id));

    }

    @PostMapping("/program/{programId}/employee/{employeeId}")

    public ResponseEntity<Object> createTraining(

            @PathVariable Long programId,

            @PathVariable Long employeeId,

            @RequestBody Training training) {

        log.info("REST request to link Program ID: {} with Employee ID: {}", programId, employeeId);



        Program program = programService.getProgramById(programId);



        EmployeeResponseDTO employeeDTO = employeeService.getEmployeeById(employeeId);

        if (employeeDTO == null) {

            log.error("Creation failed: Employee with ID {} not found", employeeId);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)

                    .body(Map.of("error", "Employee not found"));

        }



        Employee employee = new Employee();

        employee.setEmployeeId(employeeDTO.getEmployeeId());

        employee.setEmployeeName(employeeDTO.getEmployeeName());

        training.setProgram(program);

        training.setEmployee(employee);

        Training savedTraining = service.createTraining(training);

        log.info("SUCCESS: Training ID {} created for Employee {}",

                savedTraining.getTrainingId(), employeeDTO.getEmployeeName());

        return ResponseEntity.status(HttpStatus.CREATED)

                .body(Map.of(

                        "message", "Training registration successful",

                        "trainingId", savedTraining.getTrainingId(),

                        "employeeName", employeeDTO.getEmployeeName(),

                        "employeeId", employeeDTO.getEmployeeId(),

                        "programTitle", program.getProgramTitle()

                ));

    }

    @PutMapping("/updatetrainingbyid/{id}")

    public ResponseEntity<Object> updateTraining(@PathVariable Long id, @RequestBody Training training) {

        log.info("REST request to update training ID: {}", id);

        try {

            Training updated = service.updateTraining(id, training);

            log.info("Successfully updated training record ID: {}", id);

            return ResponseEntity.ok(Map.of(

                    "message", "Training record updated successfully",

                    "data", updated

            ));

        } catch (Exception e) {

            log.error("Update failed for Training ID {}: {}", id, e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

                    .body(Map.of("error", "Update failed"));

        }

    }

    @DeleteMapping("/deletetrainingbyid/{id}")

    public ResponseEntity<Object> deleteTraining(@PathVariable Long id) {

        log.warn("REST request to delete training ID: {}", id);

        service.deleteTraining(id);

        log.info("Successfully deleted training ID: {}", id);

        return ResponseEntity.ok(Map.of("message", "Training record deleted successfully"));

    }

}
