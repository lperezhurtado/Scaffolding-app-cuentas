package com.scaffolding.appcuentas.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scaffolding.appcuentas.beans.TransferBean;
import com.scaffolding.appcuentas.services.BalanceService;

@RestController
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    BalanceService balanceService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferBean transferBean) {
        return new ResponseEntity<String>(balanceService.transfer(transferBean), HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransferBean transferBean) {
        return new ResponseEntity<String>(balanceService.deposit(transferBean), HttpStatus.OK);
    }
    
}
