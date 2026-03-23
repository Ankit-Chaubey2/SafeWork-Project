package com.cts.SafeWork.exception;

public class IncidentNotFoundException extends RuntimeException {
    public IncidentNotFoundException(Long id) {
        super("Incident not found with id "+id);
    }
}
