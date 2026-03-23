package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Program;
import com.cts.SafeWork.exception.ProgramNotFoundException;
import com.cts.SafeWork.repository.ProgramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j // Enables the 'log' object
public class ProgramServiceImpl implements IProgramService {

    private final ProgramRepository repository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Program> getAllPrograms() {
        log.info("Fetching all programs from database...");
        List<Program> programs = repository.findAll();
        log.info("Total programs retrieved: {}", programs.size());
        return programs;
    }

    @Override
    public Program getProgramById(Long id) {
        log.info("Attempting to find program with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("FAILED: Program with ID {} not found", id);
                    return new ProgramNotFoundException(id);
                });
    }

    @Override
    public Program createProgram(Program program) {
        log.info("Creating new program: {}", program.getProgramTitle());
        Program savedProgram = repository.save(program);
        log.info("Program created successfully with ID: {}", savedProgram.getProgramId());
        return savedProgram;
    }

    @Override
    public Program updateProgram(Long id, Program program) {
        log.info("Request to update program ID: {}", id);
        Program existing = getProgramById(id); // Already has logging inside getProgramById

        existing.setProgramTitle(program.getProgramTitle());
        existing.setProgramDescription(program.getProgramDescription());
        existing.setProgramStartDate(program.getProgramStartDate());
        existing.setProgramEndDate(program.getProgramEndDate());
        existing.setProgramStatus(program.getProgramStatus());

        Program updated = repository.save(existing);
        log.info("Successfully updated program ID: {}", id);
        return updated;
    }

    @Override
    public void deleteProgram(Long id) {
        log.warn("Request to delete program ID: {}", id);
        Program existing = getProgramById(id);
        repository.delete(existing);
        log.info("Program ID: {} deleted successfully", id);
    }
}