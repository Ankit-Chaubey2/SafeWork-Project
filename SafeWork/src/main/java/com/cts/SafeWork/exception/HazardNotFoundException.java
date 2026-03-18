package com.cts.SafeWork.exception;

public class HazardNotFoundException extends RuntimeException {
    public HazardNotFoundException(Long id) {
        super("Hazard not found with id "+ id);
    }
}
