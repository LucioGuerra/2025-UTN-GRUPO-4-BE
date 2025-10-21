package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.AttributeDTO;
import org.agiles.bolsaestudiantil.entity.OfertaAttributeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfertaAttributeMapper {
    @Mapping(source = "attribute.id", target = "id")
    @Mapping(source = "attribute.name", target = "name")
    AttributeDTO toAttributeDto(OfertaAttributeEntity ofertaAttribute);
}
