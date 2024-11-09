package com.harsha.employeeworkspaceallocationapi.service;

import com.harsha.employeeworkspaceallocationapi.dto.request.WorkspaceRequest;
import com.harsha.employeeworkspaceallocationapi.dto.response.EmployeeResponse;
import com.harsha.employeeworkspaceallocationapi.dto.response.WorkspaceResponse;
import com.harsha.employeeworkspaceallocationapi.exception.custom.WorkspaceNotFound;
import com.harsha.employeeworkspaceallocationapi.model.Employee;
import com.harsha.employeeworkspaceallocationapi.model.Workspace;
import com.harsha.employeeworkspaceallocationapi.repository.EmployeeRepository;
import com.harsha.employeeworkspaceallocationapi.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepo;
    private final EmployeeRepository employeeRepo;

    public WorkspaceResponse createWorkspace(WorkspaceRequest request) {
        Workspace workspace = Workspace.fromRequest(request);

        return convertToWorkspaceResponse(workspaceRepo.save(workspace));

    }

    public List<WorkspaceResponse> getAllWorkspaces() {
        return workspaceRepo.findAll().stream().map(this::convertToWorkspaceResponse).collect(Collectors.toList());
    }

    public WorkspaceResponse getWorkspaceById(UUID id) {
        return workspaceRepo.findById(id).map(this::convertToWorkspaceResponse).orElseThrow(() -> new WorkspaceNotFound("Workspace with id: " + id + " not found"));
    }

    public List<WorkspaceResponse> getOccupiedWorkspaces() {
        return workspaceRepo.findAllByAvailabilityFalse().stream().map(this::convertToWorkspaceResponse).collect(Collectors.toList());
    }

    public List<WorkspaceResponse> getNonOccupiedWorkspaces() {
        return workspaceRepo.findAllByAvailabilityTrue().stream().map(this::convertToWorkspaceResponse).collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeByWorkspace(UUID id) {
        Workspace workspace = workspaceRepo.findById(id).orElseThrow(() -> new WorkspaceNotFound("Workspace with id: " + id + " not found"));

        Employee employee = workspace.getEmployee();

        if (employee == null) {
            return null;
        }

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getRole(),
                employee.getWorkspace()
        );

    }

    public WorkspaceResponse updateWorkspace(UUID id, WorkspaceRequest request) {
        Workspace currentWorkspace = workspaceRepo.findById(id).orElseThrow(() -> new WorkspaceNotFound("Workspace with id: " + id + " not found"));

        currentWorkspace.setLocation(request.location());
        currentWorkspace.setType(request.type());

        return convertToWorkspaceResponse(workspaceRepo.save(currentWorkspace));
    }

    public void deleteWorkspace(UUID id) {
        if(workspaceRepo.existsById(id)) {

            Workspace workspace = workspaceRepo.findById(id).orElseThrow(() -> new WorkspaceNotFound("Workspace with id: " + id + " not found"));

            Employee employee;

            if(!workspace.isAvailability()) {
                employee = workspace.getEmployee();
                employee.setStatus("PENDING");
                employee.setWorkspace(null);
                employeeRepo.save(employee);
            }

            workspaceRepo.deleteById(id);
        } else {
            throw new WorkspaceNotFound("Workspace with id: " + id + " not found");
        }
    }
    private WorkspaceResponse convertToWorkspaceResponse(Workspace workspace) {
        return new WorkspaceResponse(
                workspace.getId(),
                workspace.getLocation(),
                workspace.getType(),
                workspace.isAvailability()
        );
    }
}
