package com.sgallalucas.gym_app.controllers;

import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + student.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).body(studentService.create(student));
    }
}
