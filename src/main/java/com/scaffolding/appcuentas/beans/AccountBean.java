package com.scaffolding.appcuentas.beans;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AccountBean {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime openingDate;
    
    private double interestPayment;
    private double overdraftComission;
    private List<MovementBean> movement;
    private Long idUser;
    
}
