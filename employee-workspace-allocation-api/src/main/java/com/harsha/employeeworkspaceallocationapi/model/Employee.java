package com.harsha.employeeworkspaceallocationapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.harsha.employeeworkspaceallocationapi.dto.request.EmployeeRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "employees")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is required!")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is required!")
    private String email;

    @Column(name = "dept")
    private String department;

    @Column(name = "role", nullable = false)
    @NotBlank(message = "Role is required!")
    private String role;

    @Column(name = "status")
    private String status;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "workspace_id")
    @JsonBackReference
    private Workspace workspace;

    public Employee(String name, String email, String department, String role) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.role = role;
    }

    public static Employee fromRequest(EmployeeRequest request) {
        return new Employee(request.name(), request.email(), request.department(), request.role());
    }
}
