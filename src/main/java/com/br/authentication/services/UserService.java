package com.br.authentication.services;

import com.br.authentication.dtos.CreateUserDTO;
import com.br.authentication.dtos.LoginUserDTO;
import com.br.authentication.dtos.RecoveryJWTTokenDTO;
import com.br.authentication.entities.Role;
import com.br.authentication.entities.User;
import com.br.authentication.repositories.UserRepository;
import com.br.authentication.security.configurations.SecurityConfiguration;
import com.br.authentication.security.entities.UserDetails;
import com.br.authentication.security.services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public void createUser(CreateUserDTO dto) {
        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .username(dto.username())
                .password(securityConfiguration.passwordEncoder().encode(dto.password()))
                .role(Role.valueOf(dto.role()))
                .build();

        repository.save(user);
    }

    public RecoveryJWTTokenDTO authenticateUser(LoginUserDTO dto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication authentication = authenticationManager.authenticate(token);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new RecoveryJWTTokenDTO(jwtTokenService.generateToken(userDetails));
    }
}
