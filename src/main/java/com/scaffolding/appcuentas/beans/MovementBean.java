package com.scaffolding.appcuentas.beans;

import java.time.LocalDateTime;

import com.scaffolding.appcuentas.entities.AccountEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovementBean {
    
    private LocalDateTime movementDate;
    private float amount;
    private String movementDescription;
    private AccountEntity account;
    private Long idOriginAccount;
    private Long idDestinyAccount;
    private double comission;

    public MovementBean() {}

    public MovementBean(LocalDateTime movementDate, float amount, String movementDescription, AccountEntity account,
            Long idOriginAccount, Long idDestinyAccount) {
        this.movementDate = movementDate;
        this.amount = amount;
        this.movementDescription = movementDescription;
        this.account = account;
        this.idOriginAccount = idOriginAccount;
        this.idDestinyAccount = idDestinyAccount;
    }

}
