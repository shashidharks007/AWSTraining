package com.es.security.repository;

import com.es.security.entity.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, String> {

    List<EmployeeDetails> findByReportingEmailId(String reportingEmailId);

    EmployeeDetails findByEmailId(String emailId);

    EmployeeDetails findByEmployeeCode(String employeeCode);

}
