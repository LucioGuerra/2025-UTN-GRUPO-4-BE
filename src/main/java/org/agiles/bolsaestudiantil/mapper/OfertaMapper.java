package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.AttributeDTO;
import org.agiles.bolsaestudiantil.dto.response.OfertaListaDTO;
import org.agiles.bolsaestudiantil.dto.request.OfertaRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OfertaResponseDTO;
import org.agiles.bolsaestudiantil.entity.OfertaAttributeEntity;
import org.agiles.bolsaestudiantil.entity.OfertaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AplicanteMapper.class, OfertaAttributeMapper.class, EmpresaMapper.class})
public interface OfertaMapper {
    // Para listado de ofertas (sin aplicantes)
    @Mapping(target = "atributos", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(source = "empresa", target = "empresa")
    OfertaListaDTO toDTO(OfertaEntity entity);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "aplicaciones", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "tipoContrato", ignore = true)
    @Mapping(target = "publishDate", ignore = true)
    @Mapping(target = "closeDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    OfertaEntity toEntity(OfertaListaDTO dto);

    // Para respuesta completa de oferta (con atributos)
    @Mapping(target = "attributes", expression = "java(mapAttributes(entity.getAttributes()))")
    @Mapping(target = "empresa", source = "empresa")
    OfertaResponseDTO toOfertaResponseDTO(OfertaEntity entity);

    // Método helper para mapear atributos
    default Set<AttributeDTO> mapAttributes(Set<OfertaAttributeEntity> ofertaAttributes) {
        if (ofertaAttributes == null) return new HashSet<>();
        return ofertaAttributes.stream()
            .map(oa -> {
                AttributeDTO dto = new AttributeDTO();
                dto.setId(oa.getAttribute().getId());
                dto.setName(oa.getAttribute().getName());
                return dto;
            })
            .collect(Collectors.toSet());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aplicaciones", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "publishDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "closeDate", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    OfertaEntity createOfertaRequestToEntity(OfertaRequestDTO dto);
}
