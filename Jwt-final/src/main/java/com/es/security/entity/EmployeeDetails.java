package com.es.security.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetails {

    @Id
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

    @ManyToMany(fetch= FetchType.EAGER,cascade= CascadeType.ALL)
    private List<ProjectDetails> projectDetails;

    @ManyToMany(fetch= FetchType.EAGER,cascade= CascadeType.ALL)
    private List<Skills> skillsList;

    @ManyToMany(fetch= FetchType.EAGER,cascade= CascadeType.ALL)
    private List<Certification> certificationList;

}
