package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.request.WorkExperienceRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.WorkExperienceResponseDTO;
import org.agiles.bolsaestudiantil.entity.WorkExperienceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkExperienceMapper {
    WorkExperienceResponseDTO toResponseDTO(WorkExperienceEntity entity);

    List<WorkExperienceResponseDTO> toResponseDTOList(List<WorkExperienceEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    WorkExperienceEntity requestToEntity(WorkExperienceRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    void updateEntityFromRequest(WorkExperienceRequestDTO request, @MappingTarget WorkExperienceEntity entity);
}
