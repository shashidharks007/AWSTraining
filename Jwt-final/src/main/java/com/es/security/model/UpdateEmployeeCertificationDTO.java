package com.es.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeCertificationDTO {
    private  String employeeCode;
    private String oldCertificationName;
    private String newCertificationName;
}
