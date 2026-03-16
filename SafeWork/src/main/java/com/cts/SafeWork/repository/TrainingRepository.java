package com.cts.SafeWork.repository;

import com.cts.SafeWork.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    // additional queries can be added here
}
