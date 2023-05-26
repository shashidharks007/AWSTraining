package com.es.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.UniqueKey;

import java.util.concurrent.locks.Condition;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skills {
    @Id
    private Long skillID;
    private String skillName;


}
