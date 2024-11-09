package com.harsha.employeeworkspaceallocationapi.exception;

import com.harsha.employeeworkspaceallocationapi.exception.custom.EmployeeNotFound;
import com.harsha.employeeworkspaceallocationapi.exception.custom.WorkspaceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFound.class)
    public ResponseEntity<Object> employeeNotFound(EmployeeNotFound employeeNotFound) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", employeeNotFound.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WorkspaceNotFound.class)
    public ResponseEntity<Object> workspaceNotFound(WorkspaceNotFound workspaceNotFound) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", workspaceNotFound.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
