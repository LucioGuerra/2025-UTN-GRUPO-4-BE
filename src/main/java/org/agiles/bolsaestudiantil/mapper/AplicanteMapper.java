package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.AplicanteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AplicanteMapper {
    @Mapping(source = "id", target = "usuarioId")
    @Mapping(target = "cartaPresentacion", ignore = true)
    @Mapping(target = "fechaAplicacion", ignore = true)
    @Mapping(target = "ofertaId", ignore = true)
    AplicanteDTO toDTO(AplicanteEntity entity);

    @Mapping(target = "ofertas", ignore = true)
    @Mapping(source = "usuarioId", target = "id")
    AplicanteEntity toEntity(AplicanteDTO dto);
}
