package com.scaffolding.appcuentas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scaffolding.appcuentas.entities.MovementEntity;


public interface MovementRepository extends JpaRepository<MovementEntity, Long> {
    
    Page<MovementEntity> findByAccountId(Long idAccount, Pageable pageable);

    
    //PARA BUSCAR SI EL ULTIMO MOVIMIENTO ES IGUAL Y COMPROBAR SI HAN PASADO 5 MIN

    MovementEntity findByAmountAndIdOriginAccountAndIdDestinyAccount(double amount, Long idOriginAccount, Long idDestinyAccount);


    @Query(value = "SELECT * FROM movement WHERE id_origin_account = :idOrigin AND id_destiny_account = :idDestiny AND amount = :amount ORDER BY movement_date DESC LIMIT 1", nativeQuery = true)
    MovementEntity findLastMovement(@Param("amount") double amount, @Param("idOrigin") Long idOrigin, @Param("idDestiny") Long idDestiny);
}
