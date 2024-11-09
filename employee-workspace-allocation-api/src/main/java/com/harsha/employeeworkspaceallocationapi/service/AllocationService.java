package com.harsha.employeeworkspaceallocationapi.service;

import com.harsha.employeeworkspaceallocationapi.model.Employee;
import com.harsha.employeeworkspaceallocationapi.model.Workspace;
import com.harsha.employeeworkspaceallocationapi.repository.EmployeeRepository;
import com.harsha.employeeworkspaceallocationapi.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllocationService {

    private final EmployeeRepository employeeRepo;
    private final WorkspaceRepository workspaceRepo;
    private final EmployeeService employeeService;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void allocatePendingEmployees() {
        List<Employee> pendingEmployees = employeeRepo.findAllByStatus("PENDING");

        for (Employee employee : pendingEmployees) {
            Workspace workspace = employeeService.findAvailableWorkspace(employee.getRole());
            if (workspace != null) {
                employee.setStatus("ALLOCATED");
                workspace.setAvailability(false);
                employee.setWorkspace(workspace);
                workspace.setEmployee(employee);

                employeeRepo.save(employee);
                workspaceRepo.save(workspace);
            }
        }
    }
}
