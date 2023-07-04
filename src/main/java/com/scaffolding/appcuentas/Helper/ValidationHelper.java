package com.scaffolding.appcuentas.Helper;

import java.time.LocalDateTime;

import com.scaffolding.appcuentas.exceptions.ValidationException;

public class ValidationHelper {
    
    public static void validateStringLength (String string, int min, int max) {
        if (string.length() < min || string.length() > max) {
            throw new ValidationException("Número iban no debe ser superior a 34 caract.");
        }
    }

    public static void validateDoubleIsNotNegative(double number, String msg) {
        if (number < 0 ) {
            throw new ValidationException(msg);
        }
    }

    public static void validateDateIsNotBeforeNow(LocalDateTime date) {

        
        LocalDateTime now = LocalDateTime.now();

        date.isBefore(now);
    }
}
