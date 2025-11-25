package com.sgallalucas.gym_app.repositories;

import com.sgallalucas.gym_app.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<Professor, UUID> {
}
