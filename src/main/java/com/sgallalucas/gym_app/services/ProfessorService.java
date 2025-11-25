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

    public void update(UUID id, Professor updatedProfessor) {
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found"));

        professor.setName(updatedProfessor.getName());
        professor.setEmail(updatedProfessor.getEmail());
        professor.setBirthDate(updatedProfessor.getBirthDate());
        professor.setGenre(updatedProfessor.getGenre());
        professor.setSpecialty(updatedProfessor.getSpecialty());

        professorRepository.save(professor);
    }
}
