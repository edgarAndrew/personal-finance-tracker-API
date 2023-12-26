package com.edgarAndrew.PersonalFinanceTracker.controllers;

import com.edgarAndrew.PersonalFinanceTracker.models.user.User;
import com.edgarAndrew.PersonalFinanceTracker.services.AuthenticationService;
import com.edgarAndrew.PersonalFinanceTracker.services.JwtService;
import com.edgarAndrew.PersonalFinanceTracker.DTO.auth.AuthenticationRequest;
import com.edgarAndrew.PersonalFinanceTracker.DTO.auth.AuthenticationResponse;
import com.edgarAndrew.PersonalFinanceTracker.DTO.auth.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;
    private JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticationService,JwtService jwtService){
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/load")
    public ResponseEntity<String> generateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(user.toString());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
    }
}
