package com.sgallalucas.gym_app.services;

import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student create(Student student) {
        return studentRepository.save(student);
    }
}
