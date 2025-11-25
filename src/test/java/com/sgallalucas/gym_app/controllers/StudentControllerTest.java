package com.sgallalucas.gym_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgallalucas.gym_app.controllers.dtos.StudentDTO;
import com.sgallalucas.gym_app.controllers.mappers.StudentMapper;
import com.sgallalucas.gym_app.exceptions.DuplicateRecordException;
import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private StudentMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    StudentDTO studentDTO, invalidStudentDTO, updatedDTO;
    Student student, invalidStudent, updated;

    @BeforeEach
    void setUp() {
        studentDTO = new StudentDTO(UUID.randomUUID(), "Lucas", "lucas@gmail.com", LocalDate.of(2002, 4, 26), "Male");
        invalidStudentDTO = new StudentDTO(null, null, null, null, null);
        updatedDTO = new StudentDTO(UUID.randomUUID(), "Jorge", "jorge@gmail.com", LocalDate.of(2000, 2, 2), "Male");
        student = new Student(studentDTO.id(), "Lucas", "lucas@gmail.com", LocalDate.of(2002, 4, 26), "Male");
        invalidStudent = new Student();
        updated = new Student(updatedDTO.id(), "Jorge", "jorge@gmail.com", LocalDate.of(2000, 2, 2), "Male");
    }

    @Test
    @DisplayName("Salvar aluno com dados válidos")
    void create_WithValidData_ReturnCreated() throws Exception {
        when(mapper.toEntity(studentDTO)).thenReturn(student);
        when(studentService.create(student)).thenReturn(student);

        mockMvc
                .perform(post("/students")
                        .content(objectMapper.writeValueAsString(studentDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Salvar aluno com dados inválidos")
    void create_WithInvalidData_ReturnBadRequest() throws Exception {
        when(mapper.toEntity(invalidStudentDTO)).thenReturn(invalidStudent);
        when(studentService.create(invalidStudent)).thenReturn(invalidStudent);

        mockMvc
                .perform(post("/students")
                        .content(objectMapper.writeValueAsString(invalidStudentDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Salvar aluno com dados duplicados")
    void create_DuplicateStudent_ReturnConflict() throws Exception {
        when(mapper.toEntity(studentDTO)).thenReturn(student);
        when(studentService.create(student)).thenThrow(DuplicateRecordException.class);

        mockMvc
                .perform(post("/students")
                        .content(objectMapper.writeValueAsString(studentDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Buscar aluno por id existente")
    void findById_WithExistingId_ReturnOk() throws Exception{
        when(studentService.findById(student.getId())).thenReturn(student);
        when(mapper.toDTO(student)).thenReturn(studentDTO);

        mockMvc
                .perform(get("/students/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Buscar aluno por id inexistente")
    void findById_WithUnexistingId_ReturnNotFound() throws Exception {
        when(studentService.findById(any())).thenThrow(EntityNotFoundException.class);

        mockMvc
                .perform(get("/students/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Buscar aluno por id inválido")
    void findById_WithInvalidId_ReturnBadRequest() throws Exception {
        when(studentService.findById(any())).thenThrow(RuntimeException.class);

        mockMvc
                .perform(get("/students/" + "8879872"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Atualizar aluno existente com dados válidos")
    void update_WithValidData_ReturnNoContent() throws Exception {
        when(mapper.toEntity(updatedDTO)).thenReturn(updated);
        doNothing().when(studentService).update(student.getId(), updated);

        mockMvc
                .perform(put("/students/" + student.getId())
                        .content(objectMapper.writeValueAsString(updatedDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Atualizar aluno inexistente")
    void update_UnexistingStudent_ReturnNotFound() throws Exception {
        when(mapper.toEntity(updatedDTO)).thenReturn(updated);
        doThrow(EntityNotFoundException.class).when(studentService).update(student.getId(), updated);

        mockMvc
                .perform(put("/students/" + student.getId())
                        .content(objectMapper.writeValueAsString(updatedDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Atualizar aluno existente com dados inválidos")
    void update_WithInvalidData_ReturnBadRequest() throws Exception {
        mockMvc
                .perform(put("/students/" + student.getId())
                        .content(objectMapper.writeValueAsString(invalidStudentDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Atualizar aluno com dados duplicados")
    void update_WithDuplicateData_ReturnConflict() throws Exception {
        when(mapper.toEntity(updatedDTO)).thenReturn(updated);
        doThrow(DuplicateRecordException.class).when(studentService).update(student.getId(), updated);

        mockMvc
                .perform(put("/students/" + student.getId())
                        .content(objectMapper.writeValueAsString(updatedDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deletar aluno existente")
    void delete_ExistingStudent_ReturnNoContent() throws Exception {
        doNothing().when(studentService).delete(student.getId());

        mockMvc
                .perform(delete("/students/" + student.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deletar aluno inexistente")
    void delete_UnexistingStudent_ReturnNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(studentService).delete(any());

        mockMvc
                .perform(delete("/students/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deletar aluno com id inválido")
    void delete_WithInvalidId_ReturnBadRequest() throws Exception {
        doThrow(RuntimeException.class).when(studentService).delete(any());

        mockMvc
                .perform(delete("/students/7984357982"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Buscar aluno(s) por nome")
    void findByNameLike_ReturnOk() throws Exception {

    }
}
