package com.cts.SafeWork.exception;


<<<<<<< HEAD
// it tells spring htat this 404 error
@ResponseStatus(value = HttpStatus.NOT_FOUND)
=======
>>>>>>> 27833dd11b87290b2f1c6899bfa1a61998bec803
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Employee not found with id " + id);
    }
}



