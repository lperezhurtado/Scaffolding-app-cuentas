package com.scaffolding.appcuentas.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scaffolding.appcuentas.Helper.ValidationHelper;
import com.scaffolding.appcuentas.beans.MovementBean;
import com.scaffolding.appcuentas.entities.AccountEntity;
import com.scaffolding.appcuentas.entities.MovementEntity;
import com.scaffolding.appcuentas.exceptions.ValidationException;
import com.scaffolding.appcuentas.repository.MovementRepository;

@Service
public class MovementService {
    
    @Autowired
    MovementRepository movementRepo;

    public void validateID(Long id) {
        movementRepo.findById(id)
            .orElseThrow(() -> new ValidationException("No se encuentra el id " + id));
    }

    public void validateMovement(MovementBean movement) {
        ValidationHelper.validateDoubleIsNotNegative(movement.getComission(), "Comisión negativa no válida");
        ValidationHelper.validateDoubleIsNotNegative(movement.getAmount(), "Cantidad negativa no válida");
    }

    public MovementEntity getMovement (double amount, Long idOrigin, Long idDestiny) {
        MovementEntity movement = movementRepo.findLastMovement(amount, idOrigin, idDestiny);

        // if (movement == null) {
        //     throw new ValidationException("No se ha encontrado el registro");
        // }

        return movement;
    }

    public Page<MovementEntity> getMovements (Pageable pageable, Long id) {
        return movementRepo.findByAccountId(id, pageable);
    }

    public String createMovement(double amount, String description, AccountEntity account, Long idOrigin, Long idDestiny, double comission) {
        // MovementEntity mvmnt = getMovement(amount, idOrigin, idDestiny);

        // Long lDur1 = Duration.between( mvmnt.getMovementDate(), LocalDateTime.now()).toMillis();
        
        // System.out.println("FUERA DEL IF: "+lDur1);
        // if ( lDur1 < 300000) {
        //     System.out.println("DURATION IN MILLIS : "+(lDur1 - (5 * 60000)));
        //     throw new ValidationException("No puede realizar la misma transferencia hasta pasados 5 minutos");
        // }

        MovementBean movementBean = new MovementBean(LocalDateTime.now(), (float)amount, description, account, idOrigin, idDestiny, comission);
        validateMovement(movementBean);
        MovementEntity movement = movementBeanToEntity(movementBean);

        return movementRepo.save(movement).getMovementDescription();
    }

    public MovementEntity movementBeanToEntity (MovementBean movementBean) {
        MovementEntity movementEntity = new MovementEntity();
        movementEntity.setMovementDate(movementBean.getMovementDate());
        movementEntity.setAmount(movementBean.getAmount());
        movementEntity.setMovementDescription(movementBean.getMovementDescription());
        movementEntity.setAccount(movementBean.getAccount());
        movementEntity.setIdOriginAccount(movementBean.getIdOriginAccount());
        movementEntity.setIdDestinyAccount(movementBean.getIdDestinyAccount());
        movementEntity.setComission(movementBean.getComission());

        return movementEntity;
    }

    

}
