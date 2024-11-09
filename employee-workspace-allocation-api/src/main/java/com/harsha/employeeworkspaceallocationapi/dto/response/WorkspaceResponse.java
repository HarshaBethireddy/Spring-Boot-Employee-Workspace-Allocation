package com.harsha.employeeworkspaceallocationapi.dto.response;

import com.harsha.employeeworkspaceallocationapi.model.enums.WorkspaceType;

import java.util.UUID;

public record WorkspaceResponse(UUID id, String location, WorkspaceType type, boolean availability) {
}
