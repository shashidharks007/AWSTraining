package com.es.security.testServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.es.security.entity.Certification;
import com.es.security.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.es.security.entity.EmployeeDetails;
import com.es.security.entity.ProjectDetails;
import com.es.security.entity.Skills;
import com.es.security.model.EmployeeCertificationDTO;
import com.es.security.model.EmployeeProjectDTO;
import com.es.security.model.EmployeeSkillDTO;
import com.es.security.repository.CertificationRepository;
import com.es.security.repository.EmployeeDetailsRepository;
import com.es.security.repository.ProjectDetailsRepository;
import com.es.security.repository.SkillsRepository;
import com.es.security.service.EmployeeServiceImpl;
import com.es.security.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
	
	@Mock
    private EmployeeDetailsRepository employeeDetailsRepository;

	@Mock
    private ProjectDetailsRepository projectDetailsRepository;

	@Mock
    private UserServiceImpl userService;

	@Mock
    private SkillsRepository skillRepository;
	
	@Mock
	private CertificationRepository certificationRepository;
	
	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Test
	public void addEmployeeProjectTest() throws ServiceException {
		EmployeeDetails empdetails = new EmployeeDetails();
		empdetails.setEmployeeCode("0000001001");
		empdetails.setEmailId( "excel514@gmail.com");
		empdetails.setEmployeeName("Excel");
		empdetails.setMobileNumber("74710 89573");
		empdetails.setDesignation("Project Head");
		empdetails.setDepartment("Development");
		empdetails.setEmployeeGrade( "5");
		empdetails.setJoinDate("2010-07-25 18:44:38");
		empdetails.setReportingEmailId("head514@gmail.com");
		empdetails.setGender("M");
		empdetails.setLocation("Mysore");
		List<EmployeeDetails> employeeDetails = new ArrayList<EmployeeDetails>();
		employeeDetails.add(empdetails);
		ProjectDetails prodetail = new ProjectDetails();
		prodetail.setProjectCode("PC5000010");
		prodetail.setProjectTitle("PearsonLS_Common");
		List<ProjectDetails> projectDetails = new ArrayList<ProjectDetails>();
		projectDetails.add(prodetail);
		EmployeeProjectDTO dto = new EmployeeProjectDTO();
		dto.setEmployeeCode(empdetails.getEmployeeCode());
		dto.setProjectCode(prodetail.getProjectCode());
		dto.setProjectTitle(prodetail.getProjectTitle());
		List<EmployeeProjectDTO> employeeProjectDTOs = new ArrayList<EmployeeProjectDTO>();
		employeeProjectDTOs.add(dto);
		when(employeeDetailsRepository.findByEmployeeCode("0000001001")).thenReturn(empdetails);
//		assertEquals(employeeDetails, employeeServiceImpl.addEmployeeProject(employeeProjectDTOs));
		employeeServiceImpl.addEmployeeProject(employeeProjectDTOs);
	}
	
	@Test
	public void syncEmployeesTest() throws ServiceException {
		EmployeeDetails empdetails = new EmployeeDetails();
		empdetails.setEmployeeCode("0000001001");
		empdetails.setEmailId( "excel514@gmail.com");
		empdetails.setEmployeeName("Excel");
		empdetails.setMobileNumber("74710 89573");
		empdetails.setDesignation("Project Head");
		empdetails.setDepartment("Development");
		empdetails.setEmployeeGrade( "5");
		empdetails.setJoinDate("2010-07-25 18:44:38");
		empdetails.setReportingEmailId("head514@gmail.com");
		empdetails.setGender("M");
		empdetails.setLocation("Mysore");
		List<EmployeeDetails> employeeDetails = new ArrayList<EmployeeDetails>();
		employeeDetails.add(empdetails);
		when(employeeDetailsRepository.saveAll(employeeDetails)).thenReturn(employeeDetails);
		assertEquals(employeeDetails, employeeServiceImpl.syncEmployees(employeeDetails));
	}
	
	@Test
	public void getEmployeeListTest() {
		EmployeeDetails empdetails = new EmployeeDetails();
		empdetails.setEmployeeCode("0000001001");
		empdetails.setEmailId( "excel514@gmail.com");
		empdetails.setEmployeeName("Excel");
		empdetails.setMobileNumber("74710 89573");
		empdetails.setDesignation("Project Head");
		empdetails.setDepartment("Development");
		empdetails.setEmployeeGrade( "5");
		empdetails.setJoinDate("2010-07-25 18:44:38");
		empdetails.setReportingEmailId("head514@gmail.com");
		empdetails.setGender("M");
		empdetails.setLocation("Mysore");
		List<EmployeeDetails> employeeDetails = new ArrayList<EmployeeDetails>();
		employeeDetails.add(empdetails);
		when(employeeDetailsRepository.findAll()).thenReturn(employeeDetails);
		assertEquals(employeeDetails, employeeServiceImpl.getEmployeeList());
	}
	
	@Test
	public void getProjectListTest() {
		ProjectDetails prodetail = new ProjectDetails();
		prodetail.setProjectCode("PC5000010");
		prodetail.setProjectTitle("PearsonLS_Common");
		List<ProjectDetails> projectDetails = new ArrayList<ProjectDetails>();
		projectDetails.add(prodetail);
		when(projectDetailsRepository.findAll()).thenReturn(projectDetails);
		assertEquals(projectDetails, employeeServiceImpl.getProjectList());
	}
	
	@Test
	public void addSkillsTest() throws ServiceException {
		EmployeeDetails empdetails = new EmployeeDetails();
		empdetails.setEmployeeCode("0000001001");
		empdetails.setEmailId( "excel514@gmail.com");
		empdetails.setEmployeeName("Excel");
		empdetails.setMobileNumber("74710 89573");
		empdetails.setDesignation("Project Head");
		empdetails.setDepartment("Development");
		empdetails.setEmployeeGrade( "5");
		empdetails.setJoinDate("2010-07-25 18:44:38");
		empdetails.setReportingEmailId("head514@gmail.com");
		empdetails.setGender("M");
		empdetails.setLocation("Mysore");
		Skills skills = new Skills();
		skills.setSkillName("Running");
		EmployeeSkillDTO employeeSkillDTO = new EmployeeSkillDTO();
		employeeSkillDTO.setEmployeeCode(empdetails.getEmployeeCode());
		employeeSkillDTO.setSkillID(skills.getSkillID());
		//employeeSkillDTO.setOldSkillName(skills.getSkillName());
		List<EmployeeSkillDTO> employeeSkillDTOs = new ArrayList<EmployeeSkillDTO>();
		employeeSkillDTOs.add(employeeSkillDTO);
		when(employeeDetailsRepository.findByEmployeeCode("0000001001")).thenReturn(empdetails);
//		assertEquals(skillsList, employeeServiceImpl.addSkills(employeeSkillDTOs));
		employeeServiceImpl.addSkills(employeeSkillDTOs);
	}

	@Test
	public void addCertificationsTest() throws ServiceException {
		EmployeeDetails empdetails = new EmployeeDetails();
		empdetails.setEmployeeCode("0000001001");
		empdetails.setEmailId( "excel514@gmail.com");
		empdetails.setEmployeeName("Excel");
		empdetails.setMobileNumber("74710 89573");
		empdetails.setDesignation("Project Head");
		empdetails.setDepartment("Development");
		empdetails.setEmployeeGrade( "5");
		empdetails.setJoinDate("2010-07-25 18:44:38");
		empdetails.setReportingEmailId("head514@gmail.com");
		empdetails.setGender("M");
		empdetails.setLocation("Mysore");
		Certification certification = new Certification();
//		certification.setCertificationID(1);
		certification.setCertificationName("Java");
		certification.setCertificateID("1");
		EmployeeCertificationDTO certificationDTO = new EmployeeCertificationDTO();
		certificationDTO.setEmployeeCode(empdetails.getEmployeeCode());
		certificationDTO.setCertificateID(certification.getCertificateID());
		certificationDTO.setCertificationID(certification.getCertificationID());
		certificationDTO.setCertificationName(certification.getCertificationName());
		List<EmployeeCertificationDTO> certificationDTOs = new ArrayList<EmployeeCertificationDTO>();
		certificationDTOs.add(certificationDTO);
		when(employeeDetailsRepository.findByEmployeeCode("0000001001")).thenReturn(empdetails);
//		assertEquals(skillsList, employeeServiceImpl.addSkills(employeeSkillDTOs));
		employeeServiceImpl.addCertifications(certificationDTOs);
	}

	@Test
	public void getEmployeesByIdTest() {
		EmployeeDetails empdetails = new EmployeeDetails();
		empdetails.setEmployeeCode("0000001001");
		empdetails.setEmailId( "excel514@gmail.com");
		empdetails.setEmployeeName("Excel");
		empdetails.setMobileNumber("74710 89573");
		empdetails.setDesignation("Project Head");
		empdetails.setDepartment("Development");
		empdetails.setEmployeeGrade( "5");
		empdetails.setJoinDate("2010-07-25 18:44:38");
		empdetails.setReportingEmailId("head514@gmail.com");
		empdetails.setGender("M");
		empdetails.setLocation("Mysore");
		Optional<EmployeeDetails> optional = Optional.of(empdetails);
		when(employeeDetailsRepository.findById(empdetails.getEmployeeCode())).thenReturn(optional);
		assertEquals(optional,employeeServiceImpl.getEmployeesById("0000001001") );
	}
	
	@Test
	public void getEmployeesDetailsByEmailIdTest() throws ServiceException {
		employeeServiceImpl.getEmployeesDetailsByEmailId("excel514@gmail.com");
	}
	
}
