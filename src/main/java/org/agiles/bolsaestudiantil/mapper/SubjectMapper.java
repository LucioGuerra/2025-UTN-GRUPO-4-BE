package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.SubjectResponseDTO;
import org.agiles.bolsaestudiantil.entity.StudentSubjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(source = "subject.name", target = "name")
    @Mapping(source = "subject.code", target = "code")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "id", target = "id")
    SubjectResponseDTO toResponseDTO(StudentSubjectEntity entity);
}