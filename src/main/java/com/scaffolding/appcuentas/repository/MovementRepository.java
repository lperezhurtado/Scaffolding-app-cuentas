package com.scaffolding.appcuentas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scaffolding.appcuentas.entities.MovementEntity;

public interface MovementRepository extends JpaRepository<MovementEntity, Long> {
    
    Page<MovementEntity> findByAccountId(Long idAccount, Pageable pageable);
}
