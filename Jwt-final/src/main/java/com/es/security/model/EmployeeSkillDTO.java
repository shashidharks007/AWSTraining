package com.es.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSkillDTO {
    private  String employeeCode;
    private Long skillID;
    private String skillName;
}
