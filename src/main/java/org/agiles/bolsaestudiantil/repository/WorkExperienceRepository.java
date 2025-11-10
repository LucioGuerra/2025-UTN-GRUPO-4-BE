package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.WorkExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperienceEntity, Long> {
}
