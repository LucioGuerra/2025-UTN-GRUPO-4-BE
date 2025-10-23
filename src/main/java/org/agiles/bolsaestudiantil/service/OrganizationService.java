package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.entity.OrganizationEntity;
import org.agiles.bolsaestudiantil.event.RegisterUserEvent;
import org.agiles.bolsaestudiantil.repository.OrganizationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

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
