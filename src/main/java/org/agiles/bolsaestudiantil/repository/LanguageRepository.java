package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity,Long> {
}
