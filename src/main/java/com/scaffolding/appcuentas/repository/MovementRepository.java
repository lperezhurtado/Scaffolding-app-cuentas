package com.scaffolding.appcuentas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scaffolding.appcuentas.entities.MovementEntity;

public interface MovementRepository extends JpaRepository<MovementEntity, Long> {
    
}
