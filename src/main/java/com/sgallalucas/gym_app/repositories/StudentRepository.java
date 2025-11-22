package com.sgallalucas.gym_app.repositories;

import com.sgallalucas.gym_app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByNameContainingIgnoreCase(String name);

    Student findByEmail(String email);
}
