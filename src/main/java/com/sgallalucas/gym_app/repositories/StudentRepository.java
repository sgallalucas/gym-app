package com.sgallalucas.gym_app.repositories;

import com.sgallalucas.gym_app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
