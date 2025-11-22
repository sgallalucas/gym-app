package com.sgallalucas.gym_app.services;

import com.sgallalucas.gym_app.exceptions.DuplicateRecordException;
import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.repositories.StudentRepository;
import com.sgallalucas.gym_app.validators.StudentValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentValidator studentValidator;

    Student student, invalidStudent, updatedStudent;
    List<Student> studentList;

    @BeforeEach
    void setUp() {
        student = new Student(UUID.randomUUID(), "Lucas", "lucas@gmail.com", LocalDate.of(2002, 4, 26), "Male");
        updatedStudent = new Student(UUID.randomUUID(), "Dimas", "dimas@gmail.com", LocalDate.of(2000, 1, 1), "Female");
        invalidStudent = new Student(null, null, null, null, null);
        studentList = new ArrayList<>();
    }

    @Test
    @DisplayName("Salvar aluno com dados válidos")
    void createStudent_WithValidData_ReturnStudent() {
        when(studentRepository.save(student)).thenReturn(student);
        doNothing().when(studentValidator).validation(student.getEmail());

        Student sut = studentService.create(student);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(student);
    }

    @Test
    @DisplayName("Salvar aluno com dados inválidos")
    void createStudent_WithInvalidData_ThrowsException() {
        when(studentRepository.save(invalidStudent)).thenThrow(RuntimeException.class);
        doNothing().when(studentValidator).validation(invalidStudent.getEmail());

        assertThatThrownBy(() -> studentService.create(invalidStudent)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Salvar aluno com dados duplicados")
    void create_DuplicateStudent_ThrowsException() {
        doThrow(DuplicateRecordException.class).when(studentValidator).validation(student.getEmail());

        assertThatThrownBy(() -> studentService.create(student)).isInstanceOf(DuplicateRecordException.class);
    }

    @Test
    @DisplayName("Buscar aluno por id existente")
    void findById_WithValidId_ReturnStudent() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        Student sut = studentService.findById(student.getId());

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(student);
        assertThat(sut.getEmail()).isEqualTo(student.getEmail());
    }

    @Test
    @DisplayName("Buscar aluno por id inexistente")
    void findById_WithUnexistingId_ThrowsException() {
        when(studentRepository.findById(invalidStudent.getId())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> studentService.findById(invalidStudent.getId())).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Buscar aluno com id inválido")
    void findById_WithInvalidId_ThrowsException() {
        when(studentRepository.findById(invalidStudent.getId())).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> studentService.findById(invalidStudent.getId())).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Atualizar aluno com dados válidos")
    void update_WithValidData_ReturnStudent() {
        doNothing().when(studentValidator).validation(updatedStudent.getEmail());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        studentService.update(student.getId(), updatedStudent);

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("Atualizar aluno inexistente")
    void update_WithUnexistingStudent_ThrowsException() {
        doNothing().when(studentValidator).validation(updatedStudent.getEmail());
        when(studentRepository.findById(invalidStudent.getId())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> studentService.update(invalidStudent.getId(), updatedStudent)).isInstanceOf(EntityNotFoundException.class);

        verify(studentRepository, times(0)).save(invalidStudent);
    }

    @Test
    @DisplayName("Atualizar aluno com dados inválidos")
    void update_WithInvalidData_ThrowsException() {
        doNothing().when(studentValidator).validation(invalidStudent.getEmail());
        when(studentRepository.findById(invalidStudent.getId())).thenReturn(Optional.of(invalidStudent));
        when(studentRepository.save(invalidStudent)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> studentService.update(student.getId(), invalidStudent)).isInstanceOf(RuntimeException.class);

        verify(studentRepository, times(0)).save(invalidStudent);
    }

    @Test
    @DisplayName("Atualizar aluno com dados já existentes")
    void update_DuplicateStudent_ThrowsException() {
        doThrow(DuplicateRecordException.class).when(studentValidator).validation(updatedStudent.getEmail());

        assertThatThrownBy(() -> studentService.update(student.getId(), updatedStudent)).isInstanceOf(DuplicateRecordException.class);

        verify(studentRepository, times(0)).findById(student.getId());
        verify(studentRepository, times(0)).save(student);
    }

    @Test
    @DisplayName("Deletar aluno existente")
    void delete_ExistingStudent() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        studentService.delete(student.getId());

        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    @DisplayName("Deletar aluno inexistente")
    void delete_UnexistingStudent_ThrowsException() {
        when(studentRepository.findById(invalidStudent.getId())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> studentService.delete(invalidStudent.getId())).isInstanceOf(EntityNotFoundException.class);

        verify(studentRepository, times(0)).delete(invalidStudent);
    }

    @Test
    @DisplayName("Deletar aluno com id inválido")
    void delete_WithInvalidId_ThrowsException() {
        when(studentRepository.findById(invalidStudent.getId())).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> studentService.delete(invalidStudent.getId())).isInstanceOf(RuntimeException.class);

        verify(studentRepository, times(0)).delete(invalidStudent);
    }

    @Test
    @DisplayName("Buscar aluno(s) por nome")
    void findByNameLike_ReturnStudents() {
        studentList.add(student);

        when(studentRepository.findByNameContainingIgnoreCase(student.getName())).thenReturn(studentList);

        List<Student> sut = studentService.findByNameLike(student.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.getFirst()).isEqualTo(student);
    }

    @Test
    @DisplayName("Buscar aluno(s) por nome inexistente")
    void findByNameLike_WithUnexistingName_ReturnEmpty() {
        when(studentRepository.findByNameContainingIgnoreCase(invalidStudent.getName())).thenReturn(Collections.emptyList());

        List<Student> sut = studentService.findByNameLike(invalidStudent.getName());

        assertThat(sut).isEmpty();
    }
}
