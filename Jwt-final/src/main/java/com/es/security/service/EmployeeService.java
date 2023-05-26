package com.es.security.service;

import com.es.security.entity.EmployeeDetails;
import com.es.security.entity.ProjectDetails;
import com.es.security.exception.ServiceException;
import com.es.security.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface EmployeeService {

    List<EmployeeDetails> addEmployeeProject(List<EmployeeProjectDTO> employeeProjectDTO) throws ServiceException;

    List<EmployeeDetails> syncEmployees(List<EmployeeDetails> employeeDetailsList) throws ServiceException;

    List<EmployeeDetails> getEmployeeList();

    List<ProjectDetails> getProjectList();

    List<EmployeeDetails> addSkills(List<EmployeeSkillDTO> employeeSkillDTO) throws ServiceException;

    List<EmployeeDetails> updateSkill(UpdateEmployeeSkillDTO updateEmployeeSkillDTOS) throws ServiceException;

    List<EmployeeDetails> removeSkill(EmployeeSkillDTO employeeSkillDTO) throws ServiceException;

    List<EmployeeDetails> addCertifications(List<EmployeeCertificationDTO> employeeCertificationDTO) throws ServiceException;

    List<EmployeeDetails> updateCertification(UpdateEmployeeCertificationDTO updateEmployeeCertificationDTO) throws ServiceException;

    List<EmployeeDetails> removeCertification(EmployeeCertificationDTO employeeCertificationDTO) throws ServiceException;

    Optional<EmployeeDetails> getEmployeesById(String employeeCode);

    EmployeeDashboardDTO getEmployeesDetailsByEmailId(String emailId) throws ServiceException;
}
