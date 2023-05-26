package com.es.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeSkillDTO {
    private  String employeeCode;
    private String oldSkillName;
    private String newSkillName;

}
