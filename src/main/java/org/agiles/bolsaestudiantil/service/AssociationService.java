package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.CreateAssociationRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.LinkedStudentDTO;
import org.agiles.bolsaestudiantil.entity.OrganizationEntity;
import org.agiles.bolsaestudiantil.entity.OrganizationStudentAssociationEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.mapper.AssociationMapper;
import org.agiles.bolsaestudiantil.repository.OrganizationStudentAssociationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AssociationService {

    private final OrganizationStudentAssociationRepository associationRepository;
    private final OrganizationService organizationService;
    private final StudentService studentService;
    private final AssociationMapper associationMapper;

    public LinkedStudentDTO createAssociation(Long organizationId, CreateAssociationRequestDTO request) {
        OrganizationEntity organization = organizationService.getOrganizationEntityById(organizationId);
        StudentEntity student = studentService.getStudentEntityById(request.getStudentId());

        OrganizationStudentAssociationEntity association = new OrganizationStudentAssociationEntity();
        association.setOrganization(organization);
        association.setStudent(student);
        association.setRecognitionType(request.getRecognitionType());

        OrganizationStudentAssociationEntity saved = associationRepository.save(association);
        return associationMapper.toLinkedStudentDTO(saved);
    }

    public List<LinkedStudentDTO> getAssociationsForOrganization(Long organizationId) {
        List<OrganizationStudentAssociationEntity> associations = associationRepository.findByOrganizationId(organizationId);
        return associationMapper.toLinkedStudentDTOList(associations);
    }

    public void deleteAssociation(Long associationId) {
        if (!associationRepository.existsById(associationId)) {
            throw new EntityNotFoundException("Association not found with id: " + associationId);
        }
        associationRepository.deleteById(associationId);
    }
}
