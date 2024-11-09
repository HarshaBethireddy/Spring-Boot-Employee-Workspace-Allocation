package com.harsha.employeeworkspaceallocationapi.controller;

import com.harsha.employeeworkspaceallocationapi.dto.request.EmployeeRequest;
import com.harsha.employeeworkspaceallocationapi.dto.response.EmployeeResponse;
import com.harsha.employeeworkspaceallocationapi.dto.response.WorkspaceResponse;
import com.harsha.employeeworkspaceallocationapi.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable UUID id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping("/queue/waiting")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesWaitingToBeAllocated() {
        return new ResponseEntity<>(employeeService.getAllEmployeesWaitingTobeAllocated(), HttpStatus.OK);
    }

    @GetMapping("/allocated")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesAllocated() {
        return new ResponseEntity<>(employeeService.getAllEmployeesAllocated(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployeesByDepartment(@RequestParam String department) {
        return new ResponseEntity<>(employeeService.getEmployeesByDepartment(department), HttpStatus.OK);
    }

    @GetMapping("/{id}/workspace")
    public ResponseEntity<WorkspaceResponse> getEmployeeWorkspace(@PathVariable UUID id) {
        return new ResponseEntity<>(employeeService.getEmployeeWorkspace(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable UUID id, @RequestBody EmployeeRequest request) {
        return new ResponseEntity<>(employeeService.updateEmployeeById(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
