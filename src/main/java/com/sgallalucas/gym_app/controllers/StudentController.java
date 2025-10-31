package com.sgallalucas.gym_app.controllers;

import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student s = studentService.create(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + s.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).body(s);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> findById(@PathVariable String id) {
        Optional student = studentService.findById(UUID.fromString(id));
        return ResponseEntity.ok().body(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody Student student) {
        studentService.update(UUID.fromString(id), student);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        studentService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
