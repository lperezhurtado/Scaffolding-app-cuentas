package com.scaffolding.appcuentas.beans;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MovementBean {
    
    private LocalDateTime movementDate;
    private float amount;
    private String movementDescription;

    public MovementBean() {}

    public MovementBean(LocalDateTime movementDate, float amount, String movementDescription) {
        this.movementDate = movementDate;
        this.amount = amount;
        this.movementDescription = movementDescription;
    }
    
}
