package com.sgallalucas.gym_app.services;

import com.sgallalucas.gym_app.model.Professor;
import com.sgallalucas.gym_app.repositories.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public void create(Professor professor) {
        professorRepository.save(professor);
    }

    public Professor findById(UUID id) {
        return professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found"));
    }
}
