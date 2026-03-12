package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.Program;
import com.cts.SafeWork.entity.Training;
import com.cts.SafeWork.service.ITrainingService;
import com.cts.SafeWork.service.IProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private final ITrainingService service;
    private final IProgramService programService;

    @Autowired
    public TrainingController(ITrainingService service, IProgramService programService) {
        this.service = service;
        this.programService = programService;
    }

    @GetMapping
    public List<Training> getAllTrainings() {
        return service.getAllTrainings();
    }

    @GetMapping("/{id}")
    public Training getTraining(@PathVariable Long id) {
        return service.getTrainingById(id);
    }

    @PostMapping
    public Training createTraining(@RequestParam Long programId, @RequestBody Training training) {
        Program program = programService.getProgramById(programId);
        training.setProgram(program);
        return service.createTraining(training);
    }

    @PutMapping("/{id}")
    public Training updateTraining(@PathVariable Long id, @RequestBody Training training) {
        return service.updateTraining(id, training);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        service.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }
}
