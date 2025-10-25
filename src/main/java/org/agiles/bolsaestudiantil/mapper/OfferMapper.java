package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.offer.OfferSummaryResponseDTO;
import org.agiles.bolsaestudiantil.entity.AttributeEntity;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface OfferMapper {
    
    @Mapping(source = "bidder", target = "bidder")
    @Mapping(target = "applyList", ignore = true)
    @Mapping(source = "attributes", target = "attributes", qualifiedByName = "mapAttributes")
    OfferResponseDTO toResponseDTO(OfferEntity entity);
    
    @Named("mapAttributes")
    default List<String> mapAttributes(List<AttributeEntity> attributes) {
        if (attributes == null) {
            return null;
        }
        return attributes.stream()
                .map(AttributeEntity::getName)
                .collect(Collectors.toList());
    }
    
    OfferSummaryResponseDTO toSummaryDTO(OfferEntity entity);
}
