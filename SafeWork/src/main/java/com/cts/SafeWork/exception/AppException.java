package com.cts.SafeWork.exception;

// General application exception placeholder – can be extended by specific exceptions
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
