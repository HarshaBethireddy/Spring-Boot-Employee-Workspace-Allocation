package com.harsha.employeeworkspaceallocationapi.controller;

import com.harsha.employeeworkspaceallocationapi.dto.request.WorkspaceRequest;
import com.harsha.employeeworkspaceallocationapi.dto.response.EmployeeResponse;
import com.harsha.employeeworkspaceallocationapi.dto.response.WorkspaceResponse;
import com.harsha.employeeworkspaceallocationapi.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<WorkspaceResponse> creatWorkspace(@Valid @RequestBody WorkspaceRequest request) {
        return new ResponseEntity<>(workspaceService.createWorkspace(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceResponse>> getAllWorkspaces() {
        return new ResponseEntity<>(workspaceService.getAllWorkspaces(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceResponse> getWorkspaceById(@PathVariable UUID id) {
        return new ResponseEntity<>(workspaceService.getWorkspaceById(id), HttpStatus.OK);
    }

    @GetMapping("/occupied")
    public ResponseEntity<List<WorkspaceResponse>> getOccupiedWorkspaces() {
        return new ResponseEntity<>(workspaceService.getOccupiedWorkspaces(), HttpStatus.OK);
    }

    @GetMapping("/vacant")
    public ResponseEntity<List<WorkspaceResponse>> getVacantWorkspaces() {
        return new ResponseEntity<>(workspaceService.getNonOccupiedWorkspaces(), HttpStatus.OK);
    }

    @GetMapping("/{id}/employee")
    public ResponseEntity<EmployeeResponse> getEmployeeByWorkspaceId(@PathVariable UUID id) {
        return new ResponseEntity<>(workspaceService.getEmployeeByWorkspace(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkspaceResponse> updateWorkspace(@PathVariable UUID id, @RequestBody WorkspaceRequest request) {
        return new ResponseEntity<>(workspaceService.updateWorkspace(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WorkspaceResponse> deleteWorkspace(@PathVariable UUID id) {
        workspaceService.deleteWorkspace(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
