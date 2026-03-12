package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Training;
import com.cts.SafeWork.exception.TrainingNotFoundException;
import com.cts.SafeWork.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements ITrainingService {

    private final TrainingRepository repository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Training> getAllTrainings() {
        return repository.findAll();
    }

    @Override
    public Training getTrainingById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException(id));
    }

    @Override
    public Training createTraining(Training training) {
        return repository.save(training);
    }

    @Override
    public Training updateTraining(Long id, Training training) {
        Training existing = getTrainingById(id);
        existing.setTrainingCompletionDate(training.getTrainingCompletionDate());
        existing.setTrainingStatus(training.getTrainingStatus());
        existing.setProgram(training.getProgram());
        existing.setEmployee(training.getEmployee());
        return repository.save(existing);
    }

    @Override
    public void deleteTraining(Long id) {
        Training existing = getTrainingById(id);
        repository.delete(existing);
    }
}
