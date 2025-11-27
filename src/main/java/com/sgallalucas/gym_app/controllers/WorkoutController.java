package com.sgallalucas.gym_app.controllers;

import com.sgallalucas.gym_app.controllers.dtos.WorkoutDTO;
import com.sgallalucas.gym_app.controllers.mappers.WorkoutMapper;
import com.sgallalucas.gym_app.model.Professor;
import com.sgallalucas.gym_app.model.Student;
import com.sgallalucas.gym_app.model.Workout;
import com.sgallalucas.gym_app.services.ProfessorService;
import com.sgallalucas.gym_app.services.StudentService;
import com.sgallalucas.gym_app.services.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class WorkoutController {
    private final WorkoutService workoutService;
    private final StudentService studentService;
    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid WorkoutDTO dto) {
        Student student = studentService.findById(UUID.fromString(dto.student()));
        Professor professor = professorService.findById(UUID.fromString(dto.professor()));
        Workout workout = new Workout(dto.id(), dto.name(), dto.description(), dto.type(), student, professor);
        workoutService.create(workout);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + workout.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }
}
