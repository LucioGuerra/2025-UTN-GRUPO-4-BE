package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.UserResponseDTO;
import org.agiles.bolsaestudiantil.entity.OrganizationEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "role", expression = "java(entity instanceof org.agiles.bolsaestudiantil.entity.StudentEntity ? \"Student\" : \"Organization\")")
    UserResponseDTO toResponseDTO(UserEntity entity);
    
    @Mapping(target = "role", constant = "Student")
    UserResponseDTO studentToUserDTO(StudentEntity entity);
    
    @Mapping(target = "role", constant = "Organization")
    UserResponseDTO organizationToUserDTO(OrganizationEntity entity);
}
