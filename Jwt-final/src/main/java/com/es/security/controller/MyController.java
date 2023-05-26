package com.es.security.controller;

import com.es.security.model.*;
import com.es.security.requestandresponse.AuthenticationRequest;
import com.es.security.requestandresponse.AuthenticationResponse;
import com.es.security.requestandresponse.RegisterRequest;
import com.es.security.service.AuthenticationService;
import com.es.security.entity.EmployeeDetails;
import com.es.security.requestandresponse.ApiResponse;
import com.es.security.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/pms/hackathon")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class MyController {

    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final AuthenticationService service;
    @Autowired
    final ApiResponse apiResponse;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse request1 = service.register(request);
            request1.setEmail(request.getEmail());
            if (request1 != null) {
                apiResponse.setError(false);
                apiResponse.setMessage("User Data fetched successfully");
                apiResponse.setStatusCode(200);
                apiResponse.setResponseData(request1);
            }
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        } catch (Exception exp) {
            apiResponse.setError(true);
            apiResponse.setMessage("Failed");
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = service.authenticate(request);
            response.setEmail(request.getEmail());
            if (response != null) {
                apiResponse.setError(false);
                apiResponse.setMessage("User Data fetched successfully");
                apiResponse.setStatusCode(200);
                apiResponse.setResponseData(response);
            }
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        } catch (Exception exp) {
            apiResponse.setError(true);
            apiResponse.setMessage("Failed");
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/syncEmployeesAndProjects")
    public ResponseEntity<ApiResponse> syncEmployeesAndProjects() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String apiUrl = "https://pegtestdev.excelindia.com/pms/employees.json";
            EmployeeDetails[] employeeDetailsArray = mapper.readValue(new URL(apiUrl), EmployeeDetails[].class);
            List<EmployeeDetails> employeeDetailsList = Arrays.asList(employeeDetailsArray);

            // sync Employees
            employeeService.syncEmployees(employeeDetailsList);
            mapper = new ObjectMapper();
            apiUrl = "https://pegtestdev.excelindia.com/pms/projects.json";
            EmployeeProjectDTO[] employeeProjectDTOArray = mapper.readValue(new URL(apiUrl), EmployeeProjectDTO[].class);
            List<EmployeeProjectDTO> employeeProjectDTOList = Arrays.asList(employeeProjectDTOArray);

            // sync employee with project
            employeeService.addEmployeeProject(employeeProjectDTOList);
            apiResponse.setError(false);
            apiResponse.setMessage("Data synced successfully");
            apiResponse.setStatusCode(200);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        } catch (Exception exp) {
            System.out.println(exp);
            apiResponse.setError(true);
            apiResponse.setMessage(exp.getMessage());
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/syncEmployees")
//    public ResponseEntity<ApiResponse> syncEmployees(@RequestBody List<EmployeeDetails> employeeDetailsList) {
//        try {
//            List<EmployeeDetails> employeeDetails = employeeService.syncEmployees(employeeDetailsList);
//            if (employeeDetails != null) {
//                apiResponse.setError(false);
//                apiResponse.setMessage("EmployeesDetails Data fetched successfully");
//                apiResponse.setStatusCode(200);
//                apiResponse.setResponseData(employeeDetails);
//            }
//            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
//        } catch (Exception exp) {
//            System.out.println(exp);
//            apiResponse.setError(true);
//            apiResponse.setMessage(exp.getMessage());
//            apiResponse.setStatusCode(404);
//            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/syncEmployeeProjects")
//    public ResponseEntity<ApiResponse> syncEmployeeProjects(@RequestBody List<EmployeeProjectDTO> employeeProjectDTOList) {
//        try {
//            List<EmployeeDetails> employeeProjectDTO = employeeService.addEmployeeProject(employeeProjectDTOList);
//            if (employeeProjectDTO != null) {
//                apiResponse.setError(false);
//                apiResponse.setMessage("EmployeesDetails Data fetched successfully");
//                apiResponse.setStatusCode(200);
//                apiResponse.setResponseData(employeeProjectDTO);
//            }
//            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
//        } catch (Exception exp) {
//            System.out.println(exp);
//            apiResponse.setError(true);
//            apiResponse.setMessage(exp.getMessage());
//            apiResponse.setStatusCode(404);
//            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/getEmployeesDetailsByEmailId/{emailId}")
    public ResponseEntity<ApiResponse> getEmployeesDetailsByEmailId(@PathVariable("emailId") String emailId) {
        try {
            EmployeeDashboardDTO employeeDashboardDTO = employeeService.getEmployeesDetailsByEmailId(emailId);
            if (employeeDashboardDTO != null) {
                apiResponse.setError(false);
                apiResponse.setMessage("EmployeesDetails Data fetched successfully");
                apiResponse.setStatusCode(200);
                apiResponse.setResponseData(employeeDashboardDTO);
            }
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        } catch (Exception exp) {
            System.out.println(exp);
            apiResponse.setError(true);
            apiResponse.setMessage(exp.getMessage());
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addSkills")
    public ResponseEntity<ApiResponse> addSkills(@RequestBody List<EmployeeSkillDTO> employeeSkillDTO) {
        try {
            List<EmployeeDetails> skillList = employeeService.addSkills(employeeSkillDTO);
            if (skillList != null) {
                apiResponse.setError(false);
                apiResponse.setMessage("Data saved successfully");
                apiResponse.setStatusCode(200);
                apiResponse.setResponseData(skillList);
            }
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

        } catch (Exception exp) {
            apiResponse.setError(true);
            apiResponse.setMessage("Failed");
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateSkill")
    public ResponseEntity<ApiResponse> updateSkill(@RequestBody UpdateEmployeeSkillDTO pdateSkillDTO) {
        try {
            List<EmployeeDetails> skillList = employeeService.updateSkill(pdateSkillDTO);
            if (skillList != null) {
                apiResponse.setError(false);
                apiResponse.setMessage("Data saved successfully");
                apiResponse.setStatusCode(200);
                apiResponse.setResponseData(skillList);
            }
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

        } catch (Exception exp) {
            apiResponse.setError(true);
            apiResponse.setMessage("Failed");
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deleteSkill")
    public ResponseEntity<ApiResponse> deleteSkill(@RequestBody EmployeeSkillDTO employeeSkillDTO) {
        try {
            List<EmployeeDetails> skillList = employeeService.removeSkill(employeeSkillDTO);
            if (skillList != null) {
                apiResponse.setError(false);
                apiResponse.setMessage("Data saved successfully");
                apiResponse.setStatusCode(200);
                apiResponse.setResponseData(skillList);
            }
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

        } catch (Exception exp) {
            apiResponse.setError(true);
            apiResponse.setMessage("Failed");
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addCertifications")
    public ResponseEntity<ApiResponse> addCertifications(@RequestBody List<EmployeeCertificationDTO> employeeCertificationDTO) {
        try {
        List<EmployeeDetails> certificationList = employeeService.addCertifications(employeeCertificationDTO);
        if (certificationList != null) {
            apiResponse.setError(false);
            apiResponse.setMessage("Data saved successfully");
            apiResponse.setStatusCode(200);
            apiResponse.setResponseData(certificationList);
        }
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

        } catch (Exception exp) {
            apiResponse.setError(true);
            apiResponse.setMessage("Failed");
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateCertification")
    public ResponseEntity<ApiResponse> updateCertification(@RequestBody UpdateEmployeeCertificationDTO updateEmployeeCertificationDTO) {
        try {
            List<EmployeeDetails> certificationList = employeeService.updateCertification(updateEmployeeCertificationDTO);
            if (certificationList != null) {
                apiResponse.setError(false);
                apiResponse.setMessage("Data saved successfully");
                apiResponse.setStatusCode(200);
                apiResponse.setResponseData(certificationList);
            }
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

        } catch (Exception exp) {
            apiResponse.setError(true);
            apiResponse.setMessage("Failed");
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deleteCertification")
    public ResponseEntity<ApiResponse> deleteCertification(@RequestBody EmployeeCertificationDTO employeeCertificationDTO) {
        try {
            List<EmployeeDetails> certificationList = employeeService.removeCertification(employeeCertificationDTO);
            if (certificationList != null) {
                apiResponse.setError(false);
                apiResponse.setMessage("Data saved successfully");
                apiResponse.setStatusCode(200);
                apiResponse.setResponseData(certificationList);
            }
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

        } catch (Exception exp) {
            apiResponse.setError(true);
            apiResponse.setMessage("Failed");
            apiResponse.setStatusCode(404);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
