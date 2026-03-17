package com.cts.SafeWork.service;

import com.cts.SafeWork.entity.Training;

import java.util.List;

public interface ITrainingService {
    List<Training> getAllTrainings();
    Training getTrainingById(Long id);
    Training createTraining(Training training);
    Training updateTraining(Long id, Training training);
    void deleteTraining(Long id);
}
