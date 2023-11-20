package com.br.authentication.security.controllers;

import com.br.authentication.security.dtos.CreateUserDTO;
import com.br.authentication.security.dtos.LoginUserDTO;
import com.br.authentication.security.dtos.RecoveryJWTTokenDTO;
import com.br.authentication.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO dto) {
        service.createUser(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJWTTokenDTO> authenticateUser(@RequestBody LoginUserDTO dto) {
        RecoveryJWTTokenDTO token = service.authenticateUser(dto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/auth/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> getUserAuth() {
        return new ResponseEntity<>("User role authenticated", HttpStatus.OK);
    }

    @GetMapping("/auth/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> getAdminAuth() {
        return new ResponseEntity<>("Admin role authenticated", HttpStatus.OK);
    }
}
