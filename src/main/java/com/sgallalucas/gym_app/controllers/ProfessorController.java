package com.sgallalucas.gym_app.controllers;

import com.sgallalucas.gym_app.controllers.dtos.ProfessorDTO;
import com.sgallalucas.gym_app.controllers.mappers.ProfessorMapper;
import com.sgallalucas.gym_app.model.Professor;
import com.sgallalucas.gym_app.services.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;
    private final ProfessorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ProfessorDTO dto) {
        Professor professor = mapper.toEntity(dto);
        professorService.create(professor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + professor.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> findById(@PathVariable String id) {
        ProfessorDTO dto = mapper.toDTO(professorService.findById(UUID.fromString(id)));
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid ProfessorDTO dto) {
        Professor professor = mapper.toEntity(dto);
        professorService.update(UUID.fromString(id), professor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        professorService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

}
