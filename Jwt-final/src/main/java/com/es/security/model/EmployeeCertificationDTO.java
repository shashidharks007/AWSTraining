package com.es.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCertificationDTO {
    private  String employeeCode;
    private Long certificationID;
    private String certificationName;
    private String certificateID;
}