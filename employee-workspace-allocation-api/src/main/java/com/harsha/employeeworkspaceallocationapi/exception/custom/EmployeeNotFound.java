package com.harsha.employeeworkspaceallocationapi.exception.custom;

public class EmployeeNotFound extends RuntimeException {
    public EmployeeNotFound(String message) {
        super(message);
    }
}
