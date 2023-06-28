package com.scaffolding.appcuentas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scaffolding.appcuentas.Helper.IbanHelper;
import com.scaffolding.appcuentas.beans.AccountBean;
import com.scaffolding.appcuentas.entities.AccountEntity;
import com.scaffolding.appcuentas.repository.AccountRepository;

@Service
public class AccountService {

    //@Autowired  //SI PONGO EL AUTOWIRED NO FUNCIONA
    AccountEntity accountEntity;

    @Autowired
    AccountRepository accountRepo;

    public AccountEntity getAccount(Long id) {
        return accountRepo.findById(id).get();
    }

    public AccountEntity getAccountByIdUser(Long idUser) {
        return accountRepo.findByIdUser(idUser);
    }
    
    public String createAccount(AccountBean accountBean) {
        String numberIban = createIbanNumber();
        AccountEntity account = accountBeanToEntity(accountBean);
        account.setIban(numberIban);
        
        return accountRepo.save(account).getIban();
    }

    
    public AccountEntity accountBeanToEntity(AccountBean accountBean) {
        AccountEntity account = new AccountEntity();
        account.setOpeningDate(accountBean.getOpeningDate());
        account.setInterestPayment(accountBean.getInterestPayment());
        account.setOverdraftComission(accountBean.getOverdraftComission());
        account.setIdUser(accountBean.getIdUser());

        return account;
    }

    public String createIbanNumber() {
        String numberIban = IbanHelper.getValidIbanNumber();

        while (accountRepo.existsByIban(numberIban)) {
            numberIban = IbanHelper.getValidIbanNumber();
        }
        return numberIban;
    }

}
