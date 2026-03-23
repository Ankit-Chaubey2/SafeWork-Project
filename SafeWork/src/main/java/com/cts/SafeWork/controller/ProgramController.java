package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.Program;
import com.cts.SafeWork.service.IProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    private final IProgramService service;

    @Autowired
    public ProgramController(IProgramService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Program> getAllPrograms() {
        return service.getAllPrograms();
    }

    @GetMapping("/{id}")
    public Program getProgram(@PathVariable Long id) {
        return service.getProgramById(id);
    }

    @PostMapping
    public Program createProgram(@RequestBody Program program) {
        return service.createProgram(program);
    }

    @PutMapping("/{id}")
    public Program updateProgram(@PathVariable Long id, @RequestBody Program program) {
        return service.updateProgram(id, program);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        service.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }
}
