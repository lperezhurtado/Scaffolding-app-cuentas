package com.scaffolding.appcuentas.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scaffolding.appcuentas.beans.AccountBean;
import com.scaffolding.appcuentas.entities.AccountEntity;
import com.scaffolding.appcuentas.services.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    @Autowired
    AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountBean accountBean) {
        return new ResponseEntity<String>(accountService.createAccount(accountBean), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountEntity> getAccount(@PathVariable Long idUser) {
        return new ResponseEntity<AccountEntity>(accountService.getAccount(idUser), HttpStatus.OK);
    }

}
