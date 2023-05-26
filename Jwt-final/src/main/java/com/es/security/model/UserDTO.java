package com.es.security.model;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDTO {
    @Id
    private String email;
    private String userName;
    private String password;
}
