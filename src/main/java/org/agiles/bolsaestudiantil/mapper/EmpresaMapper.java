package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.EmpresaDTO;
import org.agiles.bolsaestudiantil.dto.response.EmpresaSimpleDTO;
import org.agiles.bolsaestudiantil.entity.EmpresaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {
    EmpresaDTO toDTO(EmpresaEntity entity);
    
    EmpresaSimpleDTO toSimpleDTO(EmpresaEntity entity);

    @Mapping(target = "ofertas", ignore = true)
    EmpresaEntity toEntity(EmpresaDTO dto);
}
