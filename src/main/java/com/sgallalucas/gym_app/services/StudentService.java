package com.sgallalucas.gym_app.services;

import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
    }

    public Student update(UUID id, Student updatedStudent) {
        Optional<Student> op = studentRepository.findById(id);
        Student student = op.get();
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setBirthDate(updatedStudent.getBirthDate());
        student.setGenre(updatedStudent.getGenre());

        return studentRepository.save(student);
    }
}
