package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.offer.OfferSummaryResponseDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface OfferMapper {
    
    @Mapping(source = "bidder", target = "bidder")
    @Mapping(target = "applyList", ignore = true)
    OfferResponseDTO toResponseDTO(OfferEntity entity);
    
    OfferSummaryResponseDTO toSummaryDTO(OfferEntity entity);
}
