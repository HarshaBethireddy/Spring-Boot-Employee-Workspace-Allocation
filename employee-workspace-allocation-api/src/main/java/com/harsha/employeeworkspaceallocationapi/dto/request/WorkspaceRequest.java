package com.harsha.employeeworkspaceallocationapi.dto.request;

import com.harsha.employeeworkspaceallocationapi.model.enums.WorkspaceType;

public record WorkspaceRequest(String location, WorkspaceType type) {
}
