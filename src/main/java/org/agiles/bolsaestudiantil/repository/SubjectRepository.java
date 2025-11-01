package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity,Long> {
    Optional<SubjectEntity> findByCode(String code);
}
