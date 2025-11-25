package com.sgallalucas.gym_app.controllers;

import com.sgallalucas.gym_app.controllers.dtos.StudentDTO;
import com.sgallalucas.gym_app.controllers.mappers.StudentMapper;
import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper mapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid StudentDTO dto) {
        Student student = mapper.toEntity(dto);
        studentService.create(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + student.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable String id) {
        Student student = studentService.findById(UUID.fromString(id));
        StudentDTO dto = mapper.toDTO(student);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid StudentDTO dto) {
        Student student = mapper.toEntity(dto);
        studentService.update(UUID.fromString(id), student);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        studentService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name")
    public ResponseEntity<List<StudentDTO>> findByNameLike(@RequestParam(required = false) String name) {
        List<Student> list = studentService.findByNameLike(name);
        return ResponseEntity.ok().body(list.stream().map(mapper::toDTO).toList());
    }
}
