package com.es.security.service;

import com.es.security.entity.Certification;
import com.es.security.entity.EmployeeDetails;
import com.es.security.entity.ProjectDetails;
import com.es.security.entity.Skills;
import com.es.security.exception.ServiceException;
import com.es.security.model.*;
import com.es.security.repository.CertificationRepository;
import com.es.security.repository.EmployeeDetailsRepository;
import com.es.security.repository.ProjectDetailsRepository;
import com.es.security.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SkillsRepository skillRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Override
    public List<EmployeeDetails> addEmployeeProject(List<EmployeeProjectDTO> employeeProjectDTO) throws ServiceException {
        List<EmployeeDetails> employeeProjectDetailsList = new ArrayList<EmployeeDetails>();
        try {
            int i = 1;
            for (EmployeeProjectDTO employeeProject : employeeProjectDTO) {
                ProjectDetails projectDetail = new ProjectDetails();
                projectDetail.setProjectCode(employeeProject.getProjectCode());
                projectDetail.setProjectTitle(employeeProject.getProjectTitle());
                Optional<ProjectDetails> projectDetails = projectDetailsRepository.findByProjectCode(employeeProject.getProjectCode());
                if (!projectDetails.isPresent()) {
                    projectDetailsRepository.save(projectDetail);
                }
                EmployeeDetails empD = updateEmployee(employeeProject.getEmployeeCode(), projectDetail);
                employeeProjectDetailsList.add(empD);
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeProjectDetailsList;
    }

    private EmployeeDetails updateEmployee(String empCode, ProjectDetails projectDetails) throws ServiceException {
        EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeCode(empCode);
        try {
            List<ProjectDetails> employeeProjectList = employeeDetails.getProjectDetails();
            if (employeeProjectList != null && employeeProjectList.size() > 0) {
                employeeDetails.setProjectDetails(employeeProjectList);
                if (!employeeProjectList.contains(projectDetails)) employeeProjectList.add(projectDetails);
            } else {
                employeeProjectList = new ArrayList<ProjectDetails>();
                employeeProjectList.add(projectDetails);
            }
            employeeDetails.setProjectDetails(employeeProjectList);
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeDetailsRepository.save(employeeDetails);
    }

    @Override
    public List<EmployeeDetails> syncEmployees(List<EmployeeDetails> employeeDetailsList) throws ServiceException {
        List<EmployeeDetails> employeeDetails = null;
        try {
            employeeDetails = employeeDetailsRepository.saveAll(employeeDetailsList);
            userService.user_Employee_Register();
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeDetails;
    }

    @Override
    public List<EmployeeDetails> getEmployeeList() {
        return employeeDetailsRepository.findAll();
    }

    @Override
    public List<ProjectDetails> getProjectList() {
        return projectDetailsRepository.findAll();
    }

    @Override
    public List<EmployeeDetails> addSkills(List<EmployeeSkillDTO> employeeSkillDTO) throws ServiceException {
        List<EmployeeDetails> employeeSkillDetailsList = new ArrayList<EmployeeDetails>();
        try {
            int i = 1;
            for (EmployeeSkillDTO employeeSkill : employeeSkillDTO) {
                Skills skills = new Skills();
                skills.setSkillID(employeeSkill.getSkillID());
                skills.setSkillName(employeeSkill.getSkillName());
                Optional<Skills> skillDetails = skillRepository.findBySkillName(employeeSkill.getSkillName());
                if (!skillDetails.isPresent()) {
                    skills.setSkillID(skillRepository.count() + 1);
                    skillRepository.save(skills);
                } else {
                    skills.setSkillID(skillDetails.get().getSkillID());
                    skills.setSkillName(skillDetails.get().getSkillName());
                }
                EmployeeDetails empD1 = updateEmployeeSkills(employeeSkill.getEmployeeCode(), skills);
                employeeSkillDetailsList.add(empD1);
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeSkillDetailsList;
    }

    private EmployeeDetails updateEmployeeSkills(String empCode, Skills skills) throws ServiceException {
        EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeCode(empCode);
        try {
            List<Skills> employeeSkillsList = employeeDetails.getSkillsList();
            if (employeeSkillsList != null && employeeSkillsList.size() > 0) {
                employeeDetails.setSkillsList(employeeSkillsList);
                if (!employeeSkillsList.contains(skills)) employeeSkillsList.add(skills);
            } else {
                employeeSkillsList = new ArrayList<Skills>();
                employeeSkillsList.add(skills);
            }
            employeeDetails.setSkillsList(employeeSkillsList);
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeDetailsRepository.save(employeeDetails);
    }

    @Override
    public List<EmployeeDetails> updateSkill(UpdateEmployeeSkillDTO updateSkillDTO) throws ServiceException {
        List<EmployeeDetails> employeeSkillDetailsList = new ArrayList<EmployeeDetails>();
        Skills skills = new Skills();
        try {
            Optional<Skills> skillDetails = skillRepository.findBySkillName(updateSkillDTO.getOldSkillName());
            if (skillDetails.isPresent()) {
                skills.setSkillID(skillDetails.get().getSkillID());
                skills.setSkillName(updateSkillDTO.getNewSkillName());
                skillRepository.save(skills);
                EmployeeDetails empD1 = employeeDetailsRepository.findByEmployeeCode(updateSkillDTO.getEmployeeCode());
                employeeSkillDetailsList.add(empD1);
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeSkillDetailsList;
    }

    @Override
    public List<EmployeeDetails> removeSkill(EmployeeSkillDTO employeeSkillDTO) throws ServiceException {
        List<EmployeeDetails> employeeSkillDetailsList = new ArrayList<EmployeeDetails>();
        Skills skills = new Skills();
        try {
            Optional<Skills> skillDetails = skillRepository.findBySkillName(employeeSkillDTO.getSkillName());
            if (skillDetails.isPresent()) {
                skills.setSkillID(skillDetails.get().getSkillID());
                skills.setSkillName(skillDetails.get().getSkillName());
                EmployeeDetails empD1 = removeEmployeeSkill(employeeSkillDTO.getEmployeeCode(), skills);
                employeeSkillDetailsList.add(empD1);
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeSkillDetailsList;
    }

    private EmployeeDetails removeEmployeeSkill(String empCode, Skills skills) throws ServiceException {
        EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeCode(empCode);
        try {
            List<Skills> employeeSkillsList = employeeDetails.getSkillsList();
            if (employeeSkillsList.contains(skills)) {
                employeeSkillsList.remove(skills);
                employeeDetails.setSkillsList(employeeSkillsList);
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeDetailsRepository.save(employeeDetails);
    }

    @Override
    public List<EmployeeDetails> addCertifications(List<EmployeeCertificationDTO> employeeCertificationDTO) throws ServiceException {
        List<EmployeeDetails> employeeCertificationDetailsList = new ArrayList<EmployeeDetails>();
        try {
            int i = 1;
            for (EmployeeCertificationDTO employeeCertification : employeeCertificationDTO) {
                Certification certification = new Certification();
                certification.setCertificationID(employeeCertification.getCertificationID());
                certification.setCertificateID(employeeCertification.getCertificateID());
                certification.setCertificationName(employeeCertification.getCertificationName());
                Optional<Certification> certificationDetails = certificationRepository.findByCertificationName(employeeCertification.getCertificationName());
                if (!certificationDetails.isPresent()) {
                    certification.setCertificationID(certificationRepository.count() + 1);
                    certificationRepository.save(certification);
                } else {
                    certification.setCertificationID(certificationDetails.get().getCertificationID());
                    certification.setCertificationName(certificationDetails.get().getCertificationName());
                    certification.setCertificateID(certificationDetails.get().getCertificateID());
                }
                EmployeeDetails employeeDetails = updateEmployeeCertifications(employeeCertification.getEmployeeCode(), certification);
                employeeCertificationDetailsList.add(employeeDetails);
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeCertificationDetailsList;
    }

    private EmployeeDetails updateEmployeeCertifications(String employeeCode, Certification certification) throws ServiceException {
        EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeCode(employeeCode);
        try {
            List<Certification> employeeCertificationList = employeeDetails.getCertificationList();
            if (employeeCertificationList != null && employeeCertificationList.size() > 0) {
                employeeDetails.setCertificationList(employeeCertificationList);
                if (!employeeCertificationList.contains(certification)) employeeCertificationList.add(certification);
            } else {
                employeeCertificationList = new ArrayList<Certification>();
                employeeCertificationList.add(certification);
            }
            employeeDetails.setCertificationList(employeeCertificationList);
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeDetailsRepository.save(employeeDetails);
    }

    @Override
    public List<EmployeeDetails> updateCertification(UpdateEmployeeCertificationDTO updateEmployeeCertificationDTO) throws ServiceException {
        List<EmployeeDetails> employeeCertificationDetailsList = new ArrayList<EmployeeDetails>();
        Certification certification = new Certification();
        try {
            Optional<Certification> certificationDetails = certificationRepository.findByCertificationName(updateEmployeeCertificationDTO.getOldCertificationName());
            if (certificationDetails.isPresent()) {
                certification.setCertificationID(certificationDetails.get().getCertificationID());
                certification.setCertificateID(certificationDetails.get().getCertificateID());
                certification.setCertificationName(updateEmployeeCertificationDTO.getNewCertificationName());
                certificationRepository.save(certification);
                EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeCode(updateEmployeeCertificationDTO.getEmployeeCode());
                employeeCertificationDetailsList.add(employeeDetails);
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeCertificationDetailsList;
    }

    @Override
    public List<EmployeeDetails> removeCertification(EmployeeCertificationDTO employeeCertificationDTO) throws ServiceException {
        List<EmployeeDetails> employeeCertificationDetailsList = new ArrayList<EmployeeDetails>();
        Certification certification = new Certification();
        try {
            Optional<Certification> certificationDetails = certificationRepository.findByCertificationName(employeeCertificationDTO.getCertificationName());
                if(certificationDetails.isPresent()){
                certification.setCertificationID(certificationDetails.get().getCertificationID());
                certification.setCertificateID(certificationDetails.get().getCertificateID());
                certification.setCertificationName(certificationDetails.get().getCertificationName());
                EmployeeDetails employeeDetails = removeEmployeeCertification(employeeCertificationDTO.getEmployeeCode(), certification);
                employeeCertificationDetailsList.add(employeeDetails);
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeCertificationDetailsList;
    }

    private EmployeeDetails removeEmployeeCertification(String employeeCode, Certification certification) throws ServiceException {
        EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeCode(employeeCode);
        try {
            List<Certification> employeeCertificationList = employeeDetails.getCertificationList();
            if (employeeCertificationList.contains(certification)) {
                employeeCertificationList.remove(certification);
                employeeDetails.setCertificationList(employeeCertificationList);
            }
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeDetailsRepository.save(employeeDetails);
    }

    public Optional<EmployeeDetails> getEmployeesById(String employeeCode) {
        return employeeDetailsRepository.findById(employeeCode);
    }

    public EmployeeDashboardDTO getEmployeesDetailsByEmailId(String emailId) throws ServiceException {
        EmployeeDashboardDTO employeeDashboardDTO = new EmployeeDashboardDTO();
        try {
            EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmailId(emailId);
            if (employeeDetails != null) {
                employeeDashboardDTO.setEmployeeDetails(employeeDetails);
                EmployeeDetails managerDetails = employeeDetailsRepository.findByEmailId(employeeDetails.getReportingEmailId());
                employeeDashboardDTO.setReportingTo(managerDetails != null ? managerDetails.getEmployeeName() : "Not Assigned Yet");
            }
            System.out.println("Getting Reportees List by email ID " + emailId);
            employeeDashboardDTO.setReportingList(employeeDetailsRepository.findByReportingEmailId(emailId));
            employeeDashboardDTO.setReporteesSkillList(skillRepository.findSkillByReportingEmail(emailId));
            employeeDashboardDTO.setReporteesCertificationList(certificationRepository.findCertificateByReportingEmail(emailId));
        } catch (Exception e) {
            throw new ServiceException();
        }
        return employeeDashboardDTO;
    }
}
