package com.es.security.repository;


import com.es.security.entity.ProjectDetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, String> {

	Optional<ProjectDetails> findByProjectCode(String projectCode);
}
