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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movement")
@JsonIgnoreProperties({"hibernateLazyInitialize", "handler"})
public class MovementEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime movementDate;
    private float amount;
    private String movementDescription;

    @JsonIgnore  //SOLUCION a Cannot call sendError() after the response has been committed - https://stackoverflow.com/questions/20813496/tomcat-exception-cannot-call-senderror-after-the-response-has-been-committed
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account")
    private AccountEntity account;

    private Long idDestinyAccount;
    private double comission;


    
}
