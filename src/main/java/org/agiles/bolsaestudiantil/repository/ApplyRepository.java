package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.ApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<ApplyEntity,Long>, JpaSpecificationExecutor<ApplyEntity> {
}
