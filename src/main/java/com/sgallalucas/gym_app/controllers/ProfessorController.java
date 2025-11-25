package com.sgallalucas.gym_app.controllers;

import com.sgallalucas.gym_app.controllers.dtos.ProfessorDTO;
import com.sgallalucas.gym_app.controllers.mappers.ProfessorMapper;
import com.sgallalucas.gym_app.model.Professor;
import com.sgallalucas.gym_app.services.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
