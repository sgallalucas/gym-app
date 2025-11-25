package com.sgallalucas.gym_app.repositories;

import com.sgallalucas.gym_app.model.Student;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    Student student, student2, updatedStudent, invalidStudent;
    List<Student> studentList;

    @BeforeEach
    void setUp() {
        student = new Student(null, "Lucas", "lucas@gmail.com", LocalDate.of(2002, 4, 26), "Male");
        student2 = new Student(null, "Lucas", "lucas@gmail.com", LocalDate.of(2002, 4, 26), "Male");
        updatedStudent = new Student(null, "Dimas", "dimas@gmail.com", LocalDate.of(2000, 1, 1), "Female");
        invalidStudent = new Student(null, null, null, null, null);
        studentList = new ArrayList<>();
    }

    @Test
    @DisplayName("Salvar aluno com dados válidos")
    void create_WihtValidData_ReturnStudent() {
        studentRepository.save(student);

        Student sut = testEntityManager.find(Student.class, student.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getEmail()).isEqualTo(student.getEmail());
    }

    @Test
    @DisplayName("Salvar aluno com dados inválidos")
    void create_WithInvalidData_ThrowsException() {
        studentRepository.save(invalidStudent);

        assertThatThrownBy(() -> studentRepository.flush()).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Salvar aluno com dados duplicados")
    void create_DuplicateStudent_ThrowsException() {
        testEntityManager.persistAndFlush(student);

        studentRepository.save(student2);

        assertThatThrownBy(() -> studentRepository.flush()).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Buscar aluno por id existente")
    void findById_WithExistingId_ReturnStudent() {
        testEntityManager.persistAndFlush(student);

        Optional<Student> sut = studentRepository.findById(student.getId());

        assertThat(sut).isNotEmpty();
    }

    @Test
    @DisplayName("Buscar aluno por id inexistente")
    void findById_WithUnexistingId_ReturnEmpty() {
        Optional<Student> sut = studentRepository.findById(UUID.randomUUID());

        assertThat(sut).isEmpty();
    }

    @Test
    @DisplayName("Buscar aluno por id inválido")
    void findById_WithInvalidId_ReturnEmpty() {
        Optional<Student> sut = studentRepository.findById(UUID.fromString("43e1d0ff-89b7-4c83-8645-a126c9b103f"));

        assertThat(sut).isEmpty();
    }

    @Test
    @DisplayName("Atualizar aluno existente com dados válidos")
    void update_WithValidData_ReturnStudent() {
        testEntityManager.persistAndFlush(student);
        student.setName("Dimas");

        Student sut = studentRepository.save(student);

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo("Dimas");
    }

    @Test
    @DisplayName("Atualizar aluno existente com dados inválidos")
    void update_WithInvalidData_ThrowsException() {
        testEntityManager.persistAndFlush(student);
        student.setName(null);

        Student sut = studentRepository.save(student);

        assertThatThrownBy(() -> studentRepository.flush()).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Atualizar aluno inexistente")
    void update_UnexistingStudent_ThrowsException() {
        testEntityManager.persistAndFlush(student);
        student.setId(UUID.randomUUID());

        studentRepository.save(student);

        assertThatThrownBy(() -> studentRepository.flush()).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Atualizar aluno com dados duplicados")
    void update_DuplicateData_ThrowsException() {
        testEntityManager.persistAndFlush(student);
        testEntityManager.persistAndFlush(updatedStudent);

        student.setEmail(updatedStudent.getEmail());

        studentRepository.save(student);

        assertThatThrownBy(() -> studentRepository.flush()).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Deletar aluno existente")
    void delete_ExistingStudent() {
        testEntityManager.persistAndFlush(student);

        studentRepository.delete(student);

        Student sut = testEntityManager.find(Student.class, student.getId());

        assertThat(sut).isNull();
    }

    @Test
    @DisplayName("Deletar aluno inexistente")
    void delete_UnexistingStudent_ThrowsException() {
        student.setId(UUID.randomUUID());
        Student sut = testEntityManager.find(Student.class, student.getId());

        assertThatThrownBy(() -> studentRepository.delete(sut)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Buscar aluno(s) por nome existente")
    void findByNameLike_ReturnStudent() {
        testEntityManager.persistAndFlush(student);

        studentList = studentRepository.findByNameContainingIgnoreCase("Lucas");

        assertThat(studentList).isNotEmpty();
        assertThat(studentList).hasSize(1);
    }

    @Test
    @DisplayName("Buscar aluno(s) por nome inexistente")
    void findByNameLike_ReturnEmpty() {
        studentList = studentRepository.findByNameContainingIgnoreCase("Fulano");

        assertThat(studentList).isEmpty();
    }
}
