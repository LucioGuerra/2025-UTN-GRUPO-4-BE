package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.OfferListDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferMapper {
    OfferListDTO toDTO(OfferEntity entity);

    OfferEntity toEntity(OfferListDTO dto);
}
