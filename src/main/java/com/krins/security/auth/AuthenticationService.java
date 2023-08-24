package com.krins.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krins.security.config.JwtService;
import com.krins.security.user.Role;
import com.krins.security.user.User;
import com.krins.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse register(RegisterRequest request) {
        //creates a user using the body
        var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(encoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();

        //saves the user to the database
        userRepository.save(user);

        //generates a token for the user
        var jwtToken = jwtService.generateToken(user);

        //creates a response object and returns it
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        //find the user by the email, throws id does not exist
        var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow();

        //generates a token for the user
        var jwtToken = jwtService.generateToken(user);

        //creates a response object and returns it
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

}
