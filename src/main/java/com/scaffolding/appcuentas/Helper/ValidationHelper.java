package com.scaffolding.appcuentas.Helper;

import com.scaffolding.appcuentas.exceptions.ValidationException;

public class ValidationHelper {
    
    public static void validateStringLength (String string, int min, int max) {
        if (string.length() < min || string.length() > max) {
            throw new ValidationException("Número iban no debe ser superior a 34 caract.");
        }
    }
}
