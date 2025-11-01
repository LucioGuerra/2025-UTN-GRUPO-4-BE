package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.AttributeResponseDTO;
import org.agiles.bolsaestudiantil.entity.AttributeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    AttributeResponseDTO toResponseDTO(AttributeEntity entity);
}