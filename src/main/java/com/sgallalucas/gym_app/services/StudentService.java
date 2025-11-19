package com.sgallalucas.gym_app.services;

import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.controllers.dtos.StudentDTO;
import com.sgallalucas.gym_app.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student findById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public void update(UUID id, Student updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setBirthDate(updatedStudent.getBirthDate());
        student.setGenre(updatedStudent.getGenre());

        studentRepository.save(student);
    }

    public void delete(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        studentRepository.delete(student);
    }

    public List<Student> findByNameLike(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.id());
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setBirthDate(dto.birthDate());
        student.setGenre(dto.genre());

        return student;
    }

    public StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getBirthDate(),
                student.getGenre()
        );
        return dto;
    }
}
