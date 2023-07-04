package com.scaffolding.appcuentas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

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

    @Autowired
    BalanceService balanceService;

    public AccountEntity getAccount(Long id) {
        return accountRepo.findById(id).get();
    }

    public AccountEntity getAccountByIdUser(Long idUser) {
        return accountRepo.findByIdUser(idUser);
    }

    @Transactional
    public String createAccount(AccountBean accountBean) {
        String numberIban = createNextValidIban();
        AccountEntity account = accountBeanToEntity(accountBean, numberIban);
        
        Long id = accountRepo.save(account).getId();
        balanceService.createBalance(id);
        return numberIban;
    }

    @Transactional
    public String deleteAccountByIdUser(Long idUser) {
        accountRepo.deleteByIdUser(idUser);
        return "Cuenta del usuario con ID "+idUser+" eliminada";
    }


    public AccountEntity accountBeanToEntity(AccountBean accountBean, String iban) {
        AccountEntity account = new AccountEntity();
        account.setIban(iban);
        account.setOpeningDate(accountBean.getOpeningDate());
        account.setInterestPayment(accountBean.getInterestPayment());
        account.setOverdraftComission(accountBean.getOverdraftComission());
        account.setIdUser(accountBean.getIdUser());

        return account;
    }

    // public String createIbanNumber() {
    //     String numberIban = IbanHelper.getValidIbanNumber();

    //     while (accountRepo.existsByIban(numberIban)) {
    //         numberIban = IbanHelper.getValidIbanNumber();
    //     }
    //     return numberIban;
    // }

    public String createNextValidIban() {
        return IbanHelper.getNextValidIbanNumber(findLastIbanOnDDBB());
    }

    public String findLastIbanOnDDBB () {
        return accountRepo.findByLastIban();
    }

}
