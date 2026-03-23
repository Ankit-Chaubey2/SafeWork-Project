package com.cts.SafeWork.exception;

public class InvalidEmployeeException extends RuntimeException {
    public InvalidEmployeeException() {
        super("The employee id does not match the hazard reported employee");
    }
}
