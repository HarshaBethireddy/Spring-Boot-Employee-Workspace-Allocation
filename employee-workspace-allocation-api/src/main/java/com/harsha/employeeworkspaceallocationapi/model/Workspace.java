package com.harsha.employeeworkspaceallocationapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.harsha.employeeworkspaceallocationapi.dto.request.WorkspaceRequest;
import com.harsha.employeeworkspaceallocationapi.model.enums.WorkspaceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "workspaces")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "location", nullable = false, unique = true)
    @NotBlank(message = "Location of workspace is required!")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WorkspaceType type;

    @Column(name = "availability")
    private boolean availability = true;

    @OneToOne(mappedBy = "workspace", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private Employee employee;

    public Workspace(String location, WorkspaceType type) {
        this.location = location;
        this.type = type;
    }

    public static Workspace fromRequest(WorkspaceRequest request) {
        return new Workspace(request.location(), request.type());
    }
}
