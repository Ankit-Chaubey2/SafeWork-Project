package com.cts.SafeWork.exception;

public class AuditNotFoundException extends RuntimeException {

    public AuditNotFoundException(String message) {
        super(message);
    }
}