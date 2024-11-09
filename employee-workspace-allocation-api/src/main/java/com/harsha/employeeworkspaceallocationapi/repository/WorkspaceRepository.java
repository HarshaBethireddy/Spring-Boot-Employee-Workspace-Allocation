package com.harsha.employeeworkspaceallocationapi.repository;

import com.harsha.employeeworkspaceallocationapi.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
    @Query(value = "SELECT * FROM workspaces WHERE type = ?1 AND availability = true ORDER BY location LIMIT 1", nativeQuery = true)
    Workspace findFirstByTypeAndAvailabilityTrue(String type);
    List<Workspace> findAllByAvailabilityTrue();
    List<Workspace> findAllByAvailabilityFalse();
}
