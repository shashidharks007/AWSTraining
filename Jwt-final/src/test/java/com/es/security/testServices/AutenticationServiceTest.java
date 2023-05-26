package com.es.security.testServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.aspectj.weaver.loadtime.Options;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.es.security.entity.User;
import com.es.security.repository.UserRepository;
import com.es.security.requestandresponse.AuthenticationRequest;
import com.es.security.requestandresponse.RegisterRequest;
import com.es.security.service.AuthenticationService;
import com.es.security.service.JwtService;

@ExtendWith(MockitoExtension.class)
public class AutenticationServiceTest {
	@Mock
	private UserRepository repository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtService jwtService;
	@Mock
	private AuthenticationManager authenticationManager;
	@InjectMocks
	private AuthenticationService service;
	
	@Test
    public void registerTest() throws Exception {
        RegisterRequest request = new RegisterRequest("Shashiks","shashi@gamil.com","shashi");
        User user = new User("shashi@gamil.com","shashi","Shashiks");
        assertEquals(jwtService.generateToken(user), service.register(request).getToken());
    }

	@Test
    public void authenticateTest() {
        AuthenticationRequest request = new AuthenticationRequest("shashi@gamil.com","shashi","");
        User user = new User("shashi@gamil.com","shashi","Shashiks");
        Optional<User> optional = Optional.of(user);
        when(repository.findByEmail(user.getEmail())).thenReturn(optional);
        assertEquals(jwtService.generateToken(user), service.authenticate(request).getToken());
    }
}
