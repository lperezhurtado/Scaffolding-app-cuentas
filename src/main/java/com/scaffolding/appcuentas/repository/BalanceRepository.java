package com.scaffolding.appcuentas.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scaffolding.appcuentas.entities.BalanceEntity;


public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {
    
    BalanceEntity findByIdAccount(Long idAccount);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE balance SET balance = balance + ?1 WHERE id_account = ?2", nativeQuery = true)
    void addBalance(double balance, Long idAccount);

    @Transactional
    @Modifying
    @Query(value = "UPDATE balance SET balance = balance - :amount WHERE id_account = :id_account", nativeQuery = true)
    void substractBalance(@Param("amount") double balance, @Param("id_account") Long idAccount);
}
