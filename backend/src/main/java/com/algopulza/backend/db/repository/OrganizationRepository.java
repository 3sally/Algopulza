package com.algopulza.backend.db.repository;

import com.algopulza.backend.db.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByBojId(int bojId);
    Organization findByName(String name);
}
