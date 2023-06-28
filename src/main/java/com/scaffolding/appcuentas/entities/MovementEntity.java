package com.scaffolding.appcuentas.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@Table(name = "movement")
@JsonIgnoreProperties({"hibernateLazyInitialize", "handler"})
public class MovementEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime movementDate;
    private float amount;
    private String movementDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account")
    private AccountEntity account;
}
