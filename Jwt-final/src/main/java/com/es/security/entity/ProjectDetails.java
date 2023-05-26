package com.es.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetails {

    @Id
    private String projectCode;

    private String projectTitle;

}
