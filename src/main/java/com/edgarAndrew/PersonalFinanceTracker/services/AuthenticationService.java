package com.edgarAndrew.PersonalFinanceTracker.services;

import com.edgarAndrew.PersonalFinanceTracker.exceptions.user.UserNotFoundException;
import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import com.edgarAndrew.PersonalFinanceTracker.models.user.Role;
import com.edgarAndrew.PersonalFinanceTracker.repositories.UserRepository;
import com.edgarAndrew.PersonalFinanceTracker.DTO.auth.AuthenticationResponse;
import com.edgarAndrew.PersonalFinanceTracker.DTO.auth.AuthenticationRequest;
import com.edgarAndrew.PersonalFinanceTracker.DTO.auth.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository repository,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authenticationManager){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {

        var user = new User.Builder()
                .withFirstname(request.getFirstname())
                .withLastname(request.getLastname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withRole(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse.Builder().withToken(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findByEmail(request.getEmail()).orElse(null);

        if(user == null)
            throw new UserNotFoundException("Invalid Email");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse.Builder().withToken(jwtToken).build();
    }
}
