package com.harsha.employeeworkspaceallocationapi.service;

import com.harsha.employeeworkspaceallocationapi.dto.request.EmployeeRequest;
import com.harsha.employeeworkspaceallocationapi.dto.response.EmployeeResponse;
import com.harsha.employeeworkspaceallocationapi.dto.response.WorkspaceResponse;
import com.harsha.employeeworkspaceallocationapi.exception.custom.EmployeeNotFound;
import com.harsha.employeeworkspaceallocationapi.model.Employee;
import com.harsha.employeeworkspaceallocationapi.model.Workspace;
import com.harsha.employeeworkspaceallocationapi.model.enums.WorkspaceType;
import com.harsha.employeeworkspaceallocationapi.repository.EmployeeRepository;
import com.harsha.employeeworkspaceallocationapi.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final WorkspaceRepository workspaceRepo;

    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Workspace workspace = findAvailableWorkspace(request.role());
        Employee employee = Employee.fromRequest(request);

        if(workspace == null) {
            employee.setStatus("PENDING");
            employeeRepo.save(employee);
            employee.setWorkspace(null);
            return convertToEmployeeResponse(employeeRepo.save(employee));
        }
        employee.setStatus("ALLOCATED");
        workspace.setAvailability(false);
        employee.setWorkspace(workspace);
        workspace.setEmployee(employee);

        return convertToEmployeeResponse(employeeRepo.save(employee));

    }

    public Workspace findAvailableWorkspace(String role) {
        if("manager".equalsIgnoreCase(role)) {
            return workspaceRepo.findFirstByTypeAndAvailabilityTrue(WorkspaceType.PRIVATE_CABIN.name());
        } else {
            return workspaceRepo.findFirstByTypeAndAvailabilityTrue(WorkspaceType.CUBIC_DESK.name());
        }
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepo.findAll().stream().map(this::convertToEmployeeResponse).collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(UUID id) {
        return convertToEmployeeResponse(employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFound("Employee with id: " + id + " not found.")));
    }

    public List<EmployeeResponse> getAllEmployeesWaitingTobeAllocated() {
        return employeeRepo.findAllByStatus("PENDING").stream().map(this::convertToEmployeeResponse).collect(Collectors.toList());
    }

    public List<EmployeeResponse> getAllEmployeesAllocated() {
        return employeeRepo.findAllByStatus("ALLOCATED").stream().map(this::convertToEmployeeResponse).collect(Collectors.toList());
    }

    public List<EmployeeResponse> getEmployeesByDepartment(String department) {
        return employeeRepo.findByDepartment(department).stream().map(this::convertToEmployeeResponse).collect(Collectors.toList());
    }

    public WorkspaceResponse getEmployeeWorkspace(UUID id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFound("Employee with id: " + id + " not found."));
        Workspace workspace = employee.getWorkspace();
        if(workspace == null) {
            return null;
        }
        return new WorkspaceResponse(
                workspace.getId(),
                workspace.getLocation(),
                workspace.getType(),
                workspace.isAvailability()
        );
    }

    public EmployeeResponse updateEmployeeById(UUID id, EmployeeRequest request) {
        Employee currentEmployee = employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFound("Employee with id: " + id + " not found."));

        currentEmployee.setName(request.name());
        currentEmployee.setEmail(request.email());
        currentEmployee.setDepartment(request.department());
        currentEmployee.setRole(request.role());

        return convertToEmployeeResponse(employeeRepo.save(currentEmployee));
    }

    public void deleteEmployeeById(UUID id) {
        if(employeeRepo.existsById(id)) {
           Employee employee = employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFound("id: " + id + " not found."));
           if(employee.getStatus().equals("PENDING")) {
               employeeRepo.deleteById(id);
               return;
           }
           Workspace workspace = employee.getWorkspace();
           employee.setStatus("PENDING");
           employee.setWorkspace(null);
           workspace.setEmployee(null);
           workspace.setAvailability(true);
           workspaceRepo.save(workspace);
           employeeRepo.deleteById(id);
        } else {
            throw new EmployeeNotFound("Employee with id: " + id + " not found.");
        }
    }

    private EmployeeResponse convertToEmployeeResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getRole(),
                employee.getWorkspace()
        );
    }

}
