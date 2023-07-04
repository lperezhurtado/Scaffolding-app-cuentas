package com.scaffolding.appcuentas.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferBean {
    
    Double amount;
    Long idOriginAccout;
    Long idDestinyAccount;
    Double comission;
}
