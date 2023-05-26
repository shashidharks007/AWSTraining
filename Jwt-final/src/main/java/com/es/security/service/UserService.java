package com.es.security.service;


import com.es.security.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    String user_Employee_Register();
    User add_User(User user);
}
