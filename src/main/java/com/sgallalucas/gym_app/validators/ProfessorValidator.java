package com.sgallalucas.gym_app.validators;

import com.sgallalucas.gym_app.exceptions.DuplicateRecordException;
import com.sgallalucas.gym_app.model.Professor;
import com.sgallalucas.gym_app.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfessorValidator {
    private final ProfessorRepository professorRepository;

    public void validation(String email) {
        if (verification(email)) {
            throw new DuplicateRecordException("This email has already been registered");
        }
    }

    private boolean verification(String email) {
        boolean flag;
        Professor professor = professorRepository.findByEmail(email);
        return flag = (professor == null) ? false : true;
    }
}
