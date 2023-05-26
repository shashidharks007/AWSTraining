package com.es.security.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table
public class Certification {
    @Id
    private Long certificationID;
    private String certificationName;
    private String certificateID;
}
