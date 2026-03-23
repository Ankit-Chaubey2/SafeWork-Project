package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Training;
import com.cts.SafeWork.exception.TrainingNotFoundException;
import com.cts.SafeWork.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j; // SLF4J Import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j //Logging
public class TrainingServiceImpl implements ITrainingService {

    private final TrainingRepository repository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Training> getAllTrainings() {
        log.info("Fetching all training records from the database");
        List<Training> trainings = repository.findAll();
        log.info("Successfully retrieved {} training records", trainings.size());
        return trainings;
    }

    @Override
    public Training getTrainingById(Long id) {
        log.info("Searching for training with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("ERROR: Training record with ID {} not found", id);
                    return new TrainingNotFoundException(id);
                });
    }

    @Override
    public Training createTraining(Training training) {
        log.info("Saving new training record for Status: {}", training.getTrainingStatus());
        Training savedTraining = repository.save(training);
        log.info("Training record saved successfully with ID: {}", savedTraining.getTrainingId());
        return savedTraining;
    }

    @Override
    public Training updateTraining(Long id, Training training) {
        log.info("Request received to update training record ID: {}", id);
        Training existing = getTrainingById(id); // Calls the method with logging

        existing.setTrainingCompletionDate(training.getTrainingCompletionDate());
        existing.setTrainingStatus(training.getTrainingStatus());

        Training updated = repository.save(existing);
        log.info("Successfully updated training record ID: {}", id);
        return updated;
    }

    @Override
    public void deleteTraining(Long id) {
        log.warn("Attempting to delete training record ID: {}", id);
        Training existing = getTrainingById(id);
        repository.delete(existing);
        log.info("Training record ID: {} deleted successfully", id);
    }
}