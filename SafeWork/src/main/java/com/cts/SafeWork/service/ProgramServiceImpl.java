package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Program;
import com.cts.SafeWork.exception.ProgramNotFoundException;
import com.cts.SafeWork.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements IProgramService {

    private final ProgramRepository repository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Program> getAllPrograms() {
        return repository.findAll();
    }

    @Override
    public Program getProgramById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProgramNotFoundException(id));
    }

    @Override
    public Program createProgram(Program program) {
        return repository.save(program);
    }

    @Override
    public Program updateProgram(Long id, Program program) {
        Program existing = getProgramById(id);
        existing.setProgramTitle(program.getProgramTitle());
        existing.setProgramDescription(program.getProgramDescription());
        existing.setProgramStartDate(program.getProgramStartDate());
        existing.setProgramEndDate(program.getProgramEndDate());
        existing.setProgramStatus(program.getProgramStatus());
        return repository.save(existing);
    }

    @Override
    public void deleteProgram(Long id) {
        Program existing = getProgramById(id);
        repository.delete(existing);
    }
}
