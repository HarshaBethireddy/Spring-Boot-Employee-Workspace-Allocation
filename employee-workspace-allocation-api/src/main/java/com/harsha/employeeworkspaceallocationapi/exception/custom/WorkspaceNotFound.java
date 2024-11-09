package com.harsha.employeeworkspaceallocationapi.exception.custom;

public class WorkspaceNotFound extends RuntimeException {
    public WorkspaceNotFound(String message) {
        super(message);
    }
}
