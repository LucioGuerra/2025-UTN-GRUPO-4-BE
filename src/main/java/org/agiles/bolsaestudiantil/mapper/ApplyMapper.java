package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.ApplyResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.apply.ApplyForOfferResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.apply.ApplyForStudentResponseDTO;
import org.agiles.bolsaestudiantil.entity.ApplyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface ApplyMapper {
    
    @Mapping(source = "student", target = "student")
    ApplyResponseDTO toResponseDTO(ApplyEntity entity);
    
    @Mapping(source = "student", target = "student")
    ApplyForOfferResponseDTO toOfferResponseDTO(ApplyEntity entity);
    
    @Mapping(target = "offer", ignore = true)
    ApplyForStudentResponseDTO toStudentResponseDTO(ApplyEntity entity);
}
