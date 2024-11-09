package com.harsha.employeeworkspaceallocationapi.dto.response;

import com.harsha.employeeworkspaceallocationapi.model.Workspace;

import java.util.UUID;

public record EmployeeResponse(UUID id, String name, String email, String department, String role, Workspace workspace) {
}
