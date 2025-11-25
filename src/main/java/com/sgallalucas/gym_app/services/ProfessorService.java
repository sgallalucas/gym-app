package com.sgallalucas.gym_app.services;

import com.sgallalucas.gym_app.model.Professor;
import com.sgallalucas.gym_app.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public Professor create(Professor professor) {
        return professorRepository.save(professor);
    }
}
