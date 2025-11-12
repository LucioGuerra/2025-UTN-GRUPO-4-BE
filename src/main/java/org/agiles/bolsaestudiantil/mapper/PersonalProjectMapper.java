package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.request.PersonalProjectRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.PersonalProjectResponseDTO;
import org.agiles.bolsaestudiantil.entity.PersonalProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonalProjectMapper {
    PersonalProjectResponseDTO toResponseDTO(PersonalProjectEntity entity);

    List<PersonalProjectResponseDTO> toResponseDTOList(List<PersonalProjectEntity> entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    PersonalProjectEntity requestToEntity(PersonalProjectRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    void updateEntityFromRequest(PersonalProjectRequestDTO request, @MappingTarget PersonalProjectEntity entity);
}
