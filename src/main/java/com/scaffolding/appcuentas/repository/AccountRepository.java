package com.scaffolding.appcuentas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scaffolding.appcuentas.entities.AccountEntity;


public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    
    AccountEntity findByIban(String iban);
    
    boolean existsByIban(String iban);

    AccountEntity findByIdUser(Long idUser);
}
