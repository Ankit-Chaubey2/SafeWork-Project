package com.cts.SafeWork.exception;

public class InvalidEmployeeException extends RuntimeException {
    public InvalidEmployeeException(String msg) {
        super(msg);
    }
}
