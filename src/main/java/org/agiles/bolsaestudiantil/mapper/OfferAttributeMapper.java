package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.AttributeDTO;
import org.agiles.bolsaestudiantil.entity.OfferAttributeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfferAttributeMapper {
    @Mapping(source = "attribute.id", target = "id")
    @Mapping(source = "attribute.name", target = "name")
    AttributeDTO toAttributeDto(OfferAttributeEntity offerAttribute);
}
