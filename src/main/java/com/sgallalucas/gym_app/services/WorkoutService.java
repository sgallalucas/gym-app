package com.sgallalucas.gym_app.services;

import com.sgallalucas.gym_app.model.Workout;
import com.sgallalucas.gym_app.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public void create(Workout workout) {
        workoutRepository.save(workout);
    }

    public Workout findById(UUID id) {
        return workoutRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
    }
}
