package com.cts.SafeWork.controller;

import com.cts.SafeWork.entity.Program;
import com.cts.SafeWork.service.IProgramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/programs")
@Slf4j
public class ProgramController {

    private final IProgramService service;

    @Autowired
    public ProgramController(IProgramService service) {
        this.service = service;
    }

    @GetMapping("/getallprograms")
    public ResponseEntity<List<Program>> getAllPrograms() {
        log.info("Received GET request: /programs/getallprograms");
        return ResponseEntity.ok(service.getAllPrograms());
    }

    @GetMapping("/getprogrambyid/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable Long id) {
        log.info("Received GET request for ID: {}", id);
        return ResponseEntity.ok(service.getProgramById(id));
    }

    @PostMapping("/create-program")
    public ResponseEntity<Object> createProgram(@RequestBody Program program) {
        log.info("Received POST request to create program: {}", program.getProgramTitle());
        Program savedProgram = service.createProgram(program);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Program created successfully",
                        "data", savedProgram
                ));
    }

    @PutMapping("/updateprogramwithid/{id}")
    public ResponseEntity<Object> updateProgram(@PathVariable Long id, @RequestBody Program program) {
        log.info("Received PUT request to update ID: {}", id);
        Program updatedProgram = service.updateProgram(id, program);
        return ResponseEntity.ok(Map.of(
                "message", "Program ID " + id + " updated successfully",
                "data", updatedProgram
        ));
    }

    @DeleteMapping("/deleteprogrambyid/{id}")
    public ResponseEntity<Object> deleteProgram(@PathVariable Long id) {
        log.warn("Received DELETE request for ID: {}", id);
        service.deleteProgram(id);
        return ResponseEntity.ok(Map.of("message", "Program deleted successfully"));
    }
}