package com.sgallalucas.gym_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgallalucas.gym_app.controllers.dtos.StudentDTO;
import com.sgallalucas.gym_app.controllers.mappers.StudentMapper;
import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.services.StudentService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    StudentDTO studentDTO;
    Student student;

    @BeforeEach
    void setUp() {
        studentDTO = new StudentDTO(UUID.randomUUID(), "Lucas", "lucas@gmail.com", LocalDate.of(2002, 4, 26), "Male");
        student = new Student(studentDTO.id(), "Lucas", "lucas@gmail.com", LocalDate.of(2002, 4, 26), "Male");
    }

    @Test
    @DisplayName("Salvar aluno com dados válidos")
    void create_WithValidData_ReturnCreated() throws Exception {
        when(mapper.toEntity(studentDTO)).thenReturn(student);
        when(studentService.create(student)).thenReturn(student);

        mockMvc
                .perform(post("/students")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(student));
    }
}
