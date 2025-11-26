package com.sgallalucas.gym_app.services;

import com.sgallalucas.gym_app.model.Workout;
import com.sgallalucas.gym_app.repositories.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
}
