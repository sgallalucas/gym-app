package com.sgallalucas.gym_app.controllers.mappers;

import com.sgallalucas.gym_app.controllers.dtos.StudentDTO;
import com.sgallalucas.gym_app.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentDTO dto);
    StudentDTO toDTO(Student entity);
}
