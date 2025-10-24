package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.request.MateriaDTO;
import org.agiles.bolsaestudiantil.entity.MateriaXAplicanteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MateriaMapper {

    @Mapping(source = "materia.nombre", target = "nombre")
    @Mapping(source = "materia.codigo", target = "codigo")
    @Mapping(source = "nota", target = "nota")
    MateriaDTO toDTO(MateriaXAplicanteEntity entity);
}
