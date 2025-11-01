package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.StudentSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubjectEntity, Long> {
}