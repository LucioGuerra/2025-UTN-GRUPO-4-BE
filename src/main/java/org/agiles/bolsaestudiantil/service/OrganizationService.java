package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.OrganizationResponseDTO;
import org.agiles.bolsaestudiantil.entity.OrganizationEntity;
import org.agiles.bolsaestudiantil.enums.Size;
import org.agiles.bolsaestudiantil.event.RegisterUserEvent;
import org.agiles.bolsaestudiantil.repository.OrganizationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserService userService;

    public OrganizationEntity getOrganizationEntityById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + id));
    }

    public OrganizationResponseDTO getOrganizationById(Long id) {
        OrganizationEntity entity = getOrganizationEntityById(id);
        return mapToOrganizationDTO(entity);
    }

    public OrganizationResponseDTO updateOrganization(Long id, Map<String, Object> updates) {
        OrganizationEntity entity = getOrganizationEntityById(id);
        updateEntityFromMap(entity, updates);
        OrganizationEntity saved = organizationRepository.save(entity);
        return mapToOrganizationDTO(saved);
    }

    public void deleteOrganization(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new EntityNotFoundException("Organization not found with id: " + id);
        }
        organizationRepository.deleteById(id);
    }

    private void updateEntityFromMap(OrganizationEntity entity, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "name" -> entity.setName((String) value);
                    case "surname" -> entity.setSurname((String) value);
                    case "email" -> entity.setEmail((String) value);
                    case "phone" -> entity.setPhone((String) value);
                    case "location" -> entity.setLocation((String) value);
                    case "description" -> entity.setDescription((String) value);
                    case "linkedinUrl" -> entity.setLinkedinUrl((String) value);
                    case "webSiteUrl" -> entity.setWebSiteUrl((String) value);
                    case "industry" -> entity.setIndustry((String) value);
                    case "size" -> entity.setSize(Size.valueOf((String) value));
                }
            }
        });
    }

    private OrganizationResponseDTO mapToOrganizationDTO(OrganizationEntity entity) {
        OrganizationResponseDTO dto = new OrganizationResponseDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setLocation(entity.getLocation());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setImageUrl(entity.getImageUrl());
        dto.setLinkedinUrl(entity.getLinkedinUrl());
        dto.setWebSiteUrl(entity.getWebSiteUrl());
        dto.setIndustry(entity.getIndustry());
        dto.setSize(entity.getSize());
        return dto;
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
