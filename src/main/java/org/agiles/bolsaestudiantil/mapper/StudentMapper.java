package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.StudentDTO;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO toDTO(StudentEntity entity);

    StudentEntity toEntity(StudentDTO dto);
}
