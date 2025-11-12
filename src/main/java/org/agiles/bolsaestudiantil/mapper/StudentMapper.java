package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.StudentResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.student.StudentSummaryResponseDTO;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        LanguageMapper.class,
        SubjectMapper.class,
        WorkExperienceMapper.class,
        AssociationMapper.class,
        PersonalProjectMapper.class
})
public interface StudentMapper {
    
    @Mapping(target = "role", constant = "Student")
    StudentResponseDTO toResponseDTO(StudentEntity entity);
    
    @Mapping(target = "role", constant = "Student")
    StudentSummaryResponseDTO toSummaryDTO(StudentEntity entity);
}
