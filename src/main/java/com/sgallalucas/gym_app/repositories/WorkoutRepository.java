package com.sgallalucas.gym_app.repositories;

import com.sgallalucas.gym_app.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
}
