package com.scaffolding.appcuentas.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException(String msg) {
        super("Error: "+msg);
    }
    
}
