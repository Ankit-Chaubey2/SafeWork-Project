package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.Employee;
import com.cts.SafeWork.entity.Program;
import com.cts.SafeWork.entity.Training;
import com.cts.SafeWork.service.IEmployeeService;
import com.cts.SafeWork.service.IProgramService;
import com.cts.SafeWork.service.ITrainingService;
import lombok.extern.slf4j.Slf4j; // Import
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
        log.info("REST request to fetch all trainings");
        return ResponseEntity.ok(service.getAllTrainings());
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

        log.info("REST request to create training. Program ID: {}, Employee ID: {}", programId, employeeId);

        Program program = programService.getProgramById(programId);

        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> {
                    log.error("Creation failed: Employee ID {} not found", employeeId);
                    return new RuntimeException("Employee not found with ID: " + employeeId);
                });

        training.setProgram(program);
        training.setEmployee(employee);

        Training savedTraining = service.createTraining(training);
        log.info("Training created successfully for Employee: {} in Program: {}",
                employee.getEmployeeName(), program.getProgramTitle());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Training registration successful",
                        "trainingId", savedTraining.getTrainingId(),
                        "employeeName", employee.getEmployeeName(),
                        "employeeId", employee.getEmployeeId(),
                        "programTitle", program.getProgramTitle()
                ));
    }

    @PutMapping("/updatetrainingbyid/{id}")
    public ResponseEntity<Object> updateTraining(@PathVariable Long id, @RequestBody Training training) {
        log.info("REST request to update training ID: {}", id);
        Training updated = service.updateTraining(id, training);
        return ResponseEntity.ok(Map.of(
                "message", "Training record updated successfully",
                "data", updated
        ));
    }

    @DeleteMapping("/deletetrainingbyid/{id}")
    public ResponseEntity<Object> deleteTraining(@PathVariable Long id) {
        log.warn("REST request to delete training ID: {}", id);
        service.deleteTraining(id); // Make sure this is service.deleteTraining(id) in your original code
        return ResponseEntity.ok(Map.of("message", "Training record deleted successfully"));
    }
}