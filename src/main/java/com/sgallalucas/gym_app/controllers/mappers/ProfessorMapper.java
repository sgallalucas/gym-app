package com.sgallalucas.gym_app.controllers.mappers;

import com.sgallalucas.gym_app.controllers.dtos.ProfessorDTO;
import com.sgallalucas.gym_app.model.Professor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    Professor toEntity(ProfessorDTO dto);

    ProfessorDTO toDTO(Professor entity);
}
