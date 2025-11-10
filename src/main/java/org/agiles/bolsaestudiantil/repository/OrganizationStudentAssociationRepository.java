package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.OrganizationStudentAssociationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationStudentAssociationRepository extends JpaRepository<OrganizationStudentAssociationEntity, Long> {
    List<OrganizationStudentAssociationEntity> findByOrganizationId(Long organizationId);
    List<OrganizationStudentAssociationEntity> findByStudentId(Long studentId);
}
