package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.AplicanteDTO;
import org.agiles.bolsaestudiantil.entity.AplicanteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AplicanteMapper {
    AplicanteDTO toDTO(AplicanteEntity entity);

    @Mapping(target = "ofertas", ignore = true)
    AplicanteEntity toEntity(AplicanteDTO dto);
}
