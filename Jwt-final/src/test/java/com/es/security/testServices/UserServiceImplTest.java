package com.es.security.testServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.es.security.entity.User;
import com.es.security.repository.EmployeeDetailsRepository;
import com.es.security.repository.UserRepository;
import com.es.security.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	@Mock
    private EmployeeDetailsRepository employeeDetailsRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl serviceImpl;
    
    @Test
    public void user_Employee_Register_Test(){
    	assertEquals("Users inserted successfully", serviceImpl.user_Employee_Register());
    }
    
    @Test
    public void add_User_Test(){
    	User user = new User();
    	user.setEmail("shashi@gmail.com");
    	user.setPassword("shashi");
    	user.setUserName("Shashiks");
    	when(userRepository.save(user)).thenReturn(user);
    	assertEquals(user, serviceImpl.add_User(user));
    }
}
