package com.es.security.model;

import com.es.security.entity.Certification;
import com.es.security.entity.EmployeeDetails;
import com.es.security.entity.Skills;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeDashboardDTO {
    private EmployeeDetails employeeDetails;
    private String reportingTo = "Not Assigned Yet";
    private List<EmployeeDetails> reportingList;
    private List<Skills> reporteesSkillList;
    private List<Certification> reporteesCertificationList;
}
