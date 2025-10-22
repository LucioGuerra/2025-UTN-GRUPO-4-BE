package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.viejo.EmpresaDTO;
import org.agiles.bolsaestudiantil.dto.response.viejo.EmpresaSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {
    EmpresaDTO toDTO(EmpresaEntity entity);
    
    EmpresaSimpleDTO toSimpleDTO(EmpresaEntity entity);

    @Mapping(target = "ofertas", ignore = true)
    EmpresaEntity toEntity(EmpresaDTO dto);
}
