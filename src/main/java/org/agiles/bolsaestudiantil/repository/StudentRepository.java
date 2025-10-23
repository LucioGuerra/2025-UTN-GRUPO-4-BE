package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByKeycloakId(String keycloakId);
}
