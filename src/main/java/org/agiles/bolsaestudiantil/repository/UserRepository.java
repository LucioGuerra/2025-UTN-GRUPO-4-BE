package org.agiles.bolsaestudiantil.repository;

import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    
    @Query("SELECT u FROM UserEntity u WHERE " +
           "LOWER(CONCAT(u.name, ' ', u.surname)) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.surname) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "ORDER BY " +
           "CASE " +
           "  WHEN LOWER(CONCAT(u.name, ' ', u.surname)) LIKE LOWER(CONCAT(:search, '%')) THEN 1 " +
           "  WHEN LOWER(u.name) LIKE LOWER(CONCAT(:search, '%')) THEN 2 " +
           "  WHEN LOWER(u.surname) LIKE LOWER(CONCAT(:search, '%')) THEN 3 " +
           "  ELSE 4 " +
           "END")
    Page<UserEntity> findByNameOrSurnameContaining(@Param("search") String search, Pageable pageable);
}
