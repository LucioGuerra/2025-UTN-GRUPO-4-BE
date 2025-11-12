package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.update.OrganizationUpdateRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OrganizationResponseDTO;
import org.agiles.bolsaestudiantil.entity.OrganizationEntity;
import org.agiles.bolsaestudiantil.event.RegisterUserEvent;
import org.agiles.bolsaestudiantil.mapper.OrganizationMapper;
import org.agiles.bolsaestudiantil.repository.OrganizationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserService userService;
    private final OrganizationMapper organizationMapper;

    public OrganizationEntity getOrganizationEntityById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + id));
    }

    public List<OrganizationResponseDTO> getAllOrganizations() {
        List<OrganizationEntity> organizations = organizationRepository.findAll();
        return organizations.stream()
                .map(organizationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public OrganizationResponseDTO getOrganizationById(Long id) {
        OrganizationEntity entity = getOrganizationEntityById(id);
        return organizationMapper.toResponseDTO(entity);
    }

    public OrganizationResponseDTO updateOrganization(Long id, OrganizationUpdateRequestDTO request) {
        OrganizationEntity entity = getOrganizationEntityById(id);
        updateEntityFromDTO(entity, request);
        OrganizationEntity saved = organizationRepository.save(entity);
        return organizationMapper.toResponseDTO(saved);
    }

    public void deleteOrganization(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new EntityNotFoundException("Organization not found with id: " + id);
        }
        organizationRepository.deleteById(id);
    }

    private void updateEntityFromDTO(OrganizationEntity entity, OrganizationUpdateRequestDTO request) {
        if (request.getName() != null) entity.setName(request.getName());
        if (request.getSurname() != null) entity.setSurname(request.getSurname());
        if (request.getEmail() != null) entity.setEmail(request.getEmail());
        if (request.getPhone() != null) entity.setPhone(request.getPhone());
        if (request.getLocation() != null) entity.setLocation(request.getLocation());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (request.getLinkedinUrl() != null) entity.setLinkedinUrl(request.getLinkedinUrl());
        if (request.getWebSiteUrl() != null) entity.setWebSiteUrl(request.getWebSiteUrl());
        if (request.getIndustry() != null) entity.setIndustry(request.getIndustry());
        if (request.getSize() != null) entity.setSize(request.getSize());
    }



    @Async
    @EventListener
    public void handleOrganizationRegistration(RegisterUserEvent event) {
        if ("Organization".equals(event.getRole())) {
            OrganizationEntity organization = new OrganizationEntity();
            organization.setKeycloakId(event.getKeycloakId());
            organization.setName(event.getFirstName());
            organization.setSurname(event.getLastName());
            organization.setEmail(event.getEmail());
            organization.setPhone(event.getPhone());
            organization.setLocation(event.getLocation());
            organization.setDescription(event.getDescription());
            organization.setLinkedinUrl(event.getLinkedinUrl());
            organizationRepository.save(organization);
        }
    }
}
