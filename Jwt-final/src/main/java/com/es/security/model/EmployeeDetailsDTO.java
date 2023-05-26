package com.es.security.model;


import com.es.security.entity.Certification;
import com.es.security.entity.ProjectDetails;
import com.es.security.entity.Skills;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class EmployeeDetailsDTO {

    private UUID employeeId;
    private String employeeCode;
    private String emailId;
    private String employeeName;
    private String mobileNumber;
    private String designation;
    private String department;
    private String employeeGrade;
    private String joinDate;
    private String reportingEmailId;
    private String gender;
    private String location;
    private List<ProjectDetails> projectDetails;
    private List<Skills> skillsList;
    private List<Certification> certificationList;
}
