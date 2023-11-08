package com.br.authentication.controllers;

import com.br.authentication.dtos.CreateUserDTO;
import com.br.authentication.dtos.LoginUserDTO;
import com.br.authentication.dtos.RecoveryJWTTokenDTO;
import com.br.authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO dto) {
        service.createUser(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJWTTokenDTO> authenticateUser(@RequestBody LoginUserDTO dto) {
        RecoveryJWTTokenDTO token = service.authenticateUser(dto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/auth")
    public ResponseEntity<String> getUserAuth() {
        return new ResponseEntity<>("User role authenticated", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdminAuth() {
        return new ResponseEntity<>("Admin role authenticated", HttpStatus.OK);
    }
}
