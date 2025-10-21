package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
