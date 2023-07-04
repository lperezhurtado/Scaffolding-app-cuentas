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

    public Page<MovementEntity> getMovements (Pageable pageable, Long id) {
        return movementRepo.findByAccountId(id, pageable);
    }

    public String createMovement(double amount, String description, AccountEntity account, Long idDestiny, double comission) {
        MovementBean movementBean = new MovementBean(LocalDateTime.now(), (float)amount, description, account, idDestiny, comission);
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
        movementEntity.setIdDestinyAccount(movementBean.getIdDestinyAccount());
        movementEntity.setComission(movementBean.getComission());

        return movementEntity;
    }

    

}
