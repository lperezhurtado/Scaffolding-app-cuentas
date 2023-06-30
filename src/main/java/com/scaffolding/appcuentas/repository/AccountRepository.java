package com.scaffolding.appcuentas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scaffolding.appcuentas.entities.AccountEntity;


public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    
    AccountEntity findByIban(String iban);
    
    boolean existsByIban(String iban);

    AccountEntity findByIdUser(Long idUser);

    void deleteByIdUser(Long idUser);

    @Query(value = "SELECT iban FROM account ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findByLastIban();
}
