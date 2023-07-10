package com.scaffolding.appcuentas.services;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scaffolding.appcuentas.beans.TransferBean;
import com.scaffolding.appcuentas.entities.BalanceEntity;
import com.scaffolding.appcuentas.entities.MovementEntity;
import com.scaffolding.appcuentas.exceptions.OperationException;
import com.scaffolding.appcuentas.exceptions.ValidationException;
import com.scaffolding.appcuentas.repository.AccountRepository;
import com.scaffolding.appcuentas.repository.BalanceRepository;

@Service
public class BalanceService {

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    BalanceRepository balanceRepo;

    @Autowired
    MovementService movementService;

    public void validateID(Long id) {
        if (!accountRepo.existsById(id)) {
            throw new ValidationException("No se encuentra la cuenta con ID: "+id);
        }
    }

    public void validateBothIdAreSame(Long id, Long id2) {
        if (id != id2) {
            throw new ValidationException("Los id de las cuentas no coinciden");
        }
    }

    public void validateAmount(double actualAmount, double amount) {
        checkIfAmountIsNegative(amount);
        if (amount > actualAmount) {
            throw new OperationException("No hay suficiente dinero en la cuenta");
        }
    }

    public void checkIfAmountIsNegative(double amount) {
        if (amount <= 0) {
            throw new OperationException("La cantidad no es correcta");
        }
    }

    public BalanceEntity getBalance (Long idAccount) {
        validateID(idAccount);
        return balanceRepo.findByIdAccount(idAccount);
    }


    public Long createBalance (Long idAccount) {
        BalanceEntity balance = new BalanceEntity();
        balance.setIdAccount(idAccount);

        return balanceRepo.save(balance).getId();
    }

    @Transactional
    public String transfer (TransferBean transfer) {

        validateID(transfer.getIdOriginAccout());
        validateID(transfer.getIdDestinyAccount());

        BalanceEntity balanceEntity = balanceRepo.findByIdAccount(transfer.getIdOriginAccout());
        validateAmount(balanceEntity.getBalance(), transfer.getAmount());

        balanceRepo.addBalance(transfer.getAmount(), transfer.getIdDestinyAccount());
        balanceRepo.substractBalance(transfer.getAmount(), balanceEntity.getIdAccount());

        MovementEntity mvmnt = movementService.getMovement(transfer.getAmount(), transfer.getIdOriginAccout(), transfer.getIdDestinyAccount());
        
        if (mvmnt != null) {
            Long lDur1 = Duration.between( mvmnt.getMovementDate(), LocalDateTime.now()).toMillis();
            if ( lDur1 < 300000) {
            System.out.println("DURATION IN MILLIS : "+(lDur1 - (5 * 60000)));
            throw new ValidationException("No puede realizar la misma transferencia hasta pasados 5 minutos");
            } else {
                movementService.createMovement(transfer.getAmount(), sentTranfer(transfer.getAmount(), transfer.getIdDestinyAccount()), accountRepo.findById(transfer.getIdOriginAccout()).get(), transfer.getIdOriginAccout(), transfer.getIdDestinyAccount(), transfer.getComission());
                movementService.createMovement(transfer.getAmount(), receivedTransfer(transfer.getAmount(), transfer.getIdOriginAccout()), accountRepo.findById(transfer.getIdDestinyAccount()).get(), transfer.getIdOriginAccout(), transfer.getIdDestinyAccount(), 0);
            }
        } else {
            movementService.createMovement(transfer.getAmount(), sentTranfer(transfer.getAmount(), transfer.getIdDestinyAccount()), accountRepo.findById(transfer.getIdOriginAccout()).get(), transfer.getIdOriginAccout(), transfer.getIdDestinyAccount(), transfer.getComission());
            movementService.createMovement(transfer.getAmount(), receivedTransfer(transfer.getAmount(), transfer.getIdOriginAccout()), accountRepo.findById(transfer.getIdDestinyAccount()).get(), transfer.getIdOriginAccout(), transfer.getIdDestinyAccount(), 0);
        }
        
        return "Se ha hecho transferencia";
    }

    public String withDraw (TransferBean transfer) {
        validateID(transfer.getIdOriginAccout());
        validateBothIdAreSame(transfer.getIdOriginAccout(), transfer.getIdDestinyAccount());

        BalanceEntity balanceEntity = balanceRepo.findByIdAccount(transfer.getIdOriginAccout());
        validateAmount(balanceEntity.getBalance(), transfer.getAmount());

        balanceRepo.substractBalance(transfer.getAmount(), balanceEntity.getIdAccount());

        return movementService.createMovement(transfer.getAmount(), withDrawMessage(transfer.getAmount()), accountRepo.findById(transfer.getIdOriginAccout()).get(), transfer.getIdOriginAccout(), transfer.getIdDestinyAccount(), 0);
    }

    public String deposit (TransferBean transfer) {
        validateID(transfer.getIdOriginAccout());
        validateBothIdAreSame(transfer.getIdOriginAccout(), transfer.getIdDestinyAccount());

        BalanceEntity balanceEntity = balanceRepo.findByIdAccount(transfer.getIdOriginAccout());
        checkIfAmountIsNegative(transfer.getAmount());

        balanceRepo.addBalance(transfer.getAmount(), balanceEntity.getIdAccount());

        return movementService.createMovement(transfer.getAmount(), depositMessage(transfer.getAmount()), accountRepo.findById(transfer.getIdOriginAccout()).get(), transfer.getIdOriginAccout(), transfer.getIdDestinyAccount(), 0);
    }

    
    public String withDrawMessage(double amount) {
        return "Se ha retirado la cantidad "+amount+"€";
    }
    public String depositMessage(double amount) {
        return "Se ha ingresado la cantidad "+amount+"€";
    }
    public String sentTranfer ( double amount, Long idDestiny) {
        return "Se ha enviado "+ amount+"€ a la cuenta "+ idDestiny;
    }
    public String receivedTransfer (double amount, Long idOrigin) {
        return "Se ha recibido "+ amount+"€ de la cuenta "+idOrigin;
    }
}
