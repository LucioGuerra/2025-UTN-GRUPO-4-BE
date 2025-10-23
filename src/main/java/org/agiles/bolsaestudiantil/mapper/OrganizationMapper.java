package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.OrganizationResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.organization.OrganizationSummaryResponseDTO;
import org.agiles.bolsaestudiantil.entity.OrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    
    @Mapping(target = "role", constant = "Organization")
    OrganizationResponseDTO toResponseDTO(OrganizationEntity entity);
    
    @Mapping(target = "role", constant = "Organization")
    OrganizationSummaryResponseDTO toSummaryDTO(OrganizationEntity entity);
}
