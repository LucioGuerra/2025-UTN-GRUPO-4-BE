package org.agiles.bolsaestudiantil.mapper;

import org.agiles.bolsaestudiantil.dto.response.AssociatedCompanyDTO;
import org.agiles.bolsaestudiantil.dto.response.LinkedStudentDTO;
import org.agiles.bolsaestudiantil.entity.OrganizationStudentAssociationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssociationMapper {

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.organization.id", target = "companyId")
    @Mapping(source = "entity.organization.name", target = "companyName")
    @Mapping(source = "entity.organization.imageUrl", target = "companyImageUrl")
    @Mapping(source = "entity.organization.industry", target = "companyIndustry")
    @Mapping(source = "entity.associationDate", target = "associationDate")
    @Mapping(source = "entity.recognitionType", target = "recognitionType")
    AssociatedCompanyDTO toAssociatedCompanyDTO(OrganizationStudentAssociationEntity entity);

    List<AssociatedCompanyDTO> toAssociatedCompanyDTOList(List<OrganizationStudentAssociationEntity> entity);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.student.id", target = "studentId")
    @Mapping(source = "entity.student.name", target = "name")
    @Mapping(source = "entity.student.surname", target = "surname")
    @Mapping(source = "entity.student.imageUrl", target = "imageUrl")
    @Mapping(source = "entity.student.career", target = "career")
    @Mapping(source = "entity.student.currentYearLevel", target = "currentYearLevel")
    @Mapping(source = "entity.student.institution", target = "institution")
    @Mapping(source = "entity.associationDate", target = "associationDate")
    @Mapping(source = "entity.recognitionType", target = "recognitionType")
    LinkedStudentDTO toLinkedStudentDTO(OrganizationStudentAssociationEntity entity);

    List<LinkedStudentDTO> toLinkedStudentDTOList(List<OrganizationStudentAssociationEntity> entity);
}
