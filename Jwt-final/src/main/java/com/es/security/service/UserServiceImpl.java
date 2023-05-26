package com.es.security.service;

import com.es.security.entity.User;
import com.es.security.repository.EmployeeDetailsRepository;
import com.es.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService{
	
    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public String user_Employee_Register(){
        try{
            userRepository.user_Employee();
        }catch (Exception e){
            return "There is an exception";
        }
        return "Users inserted successfully";
    }
    @Override
    public User add_User(User user) {
        return userRepository.save(user);
    }
}
