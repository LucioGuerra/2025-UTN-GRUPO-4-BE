package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.LanguageResponseDTO;
import org.agiles.bolsaestudiantil.entity.LanguageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    
    LanguageResponseDTO toResponseDTO(LanguageEntity entity);
}
