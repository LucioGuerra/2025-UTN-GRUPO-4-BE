package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, String> {
}
