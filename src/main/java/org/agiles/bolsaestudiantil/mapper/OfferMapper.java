package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, OfferAttributeMapper.class})
public interface OfferMapper {
    OfferDTO toDTO(OfferEntity entity);

    OfferEntity toEntity(OfferDTO dto);

    OfferResponseDTO toOfferResponseDTO(OfferEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    OfferEntity createOfferRequestToEntity(OfferRequestDTO dto);
}
