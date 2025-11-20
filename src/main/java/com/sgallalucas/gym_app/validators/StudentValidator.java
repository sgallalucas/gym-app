package com.sgallalucas.gym_app.validators;

import com.sgallalucas.gym_app.exceptions.DuplicateRecordException;
import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentValidator {
    private final StudentRepository studentRepository;

    public void validation(String email) {
        if (verification(email)) {
            throw new DuplicateRecordException("This email has already been registered");
        }
    }

    private boolean verification(String email) {
        boolean flag;
        Student student = studentRepository.findByEmail(email);
        return flag = (student == null) ? false : true;
    }
}
