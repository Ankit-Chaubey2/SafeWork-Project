package com.cts.SafeWork.exception;

public class NoAuditFoundException extends RuntimeException {

    public NoAuditFoundException(String message) {
        super(message);
    }
}