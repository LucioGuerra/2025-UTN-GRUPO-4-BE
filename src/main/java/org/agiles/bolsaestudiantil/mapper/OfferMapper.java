package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;

import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, OfferAttributeMapper.class})
public interface OfferMapper {
    // Para listado de ofertas (sin estudiantes)
    OfferDTO toDTO(OfferEntity entity);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "students", ignore = true)
    OfferEntity toEntity(OfferDTO dto);

    // Para respuesta completa de oferta (con atributos)
    OfferResponseDTO toOfferResponseDTO(OfferEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "publishDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "closeDate", ignore = true)
    OfferEntity createOfferRequestToEntity(OfferRequestDTO dto);
}
