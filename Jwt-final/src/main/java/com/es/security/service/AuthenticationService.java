package com.es.security.service;

import com.es.security.exception.ServiceException;
import com.es.security.requestandresponse.AuthenticationRequest;
import com.es.security.requestandresponse.AuthenticationResponse;
import com.es.security.requestandresponse.RegisterRequest;
import com.es.security.entity.User;
import com.es.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws ServiceException {
        var user = User.builder().userName(request.getUserName()).email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())).build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var token = request.getToken();
        if (token.isEmpty()) {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();
        } else {
            if (jwtService.isTokenValid(token, user)) {
                return AuthenticationResponse.builder().token(token).build();
            } else {
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken).build();
            }
        }
    }

}
