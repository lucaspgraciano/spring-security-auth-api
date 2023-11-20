package com.br.authentication.security.services;

import com.br.authentication.security.configurations.SecurityConfiguration;
import com.br.authentication.security.dtos.CreateUserDTO;
import com.br.authentication.security.dtos.LoginUserDTO;
import com.br.authentication.security.dtos.RecoveryJWTTokenDTO;
import com.br.authentication.security.dtos.ResetEmailDto;
import com.br.authentication.security.entities.Role;
import com.br.authentication.security.entities.User;
import com.br.authentication.security.entities.UserDetails;
import com.br.authentication.security.repositories.UserRepository;
import com.br.authentication.sqs.producers.SqsMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private SqsMessageProducer producer;


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

    public void produceRequestResetPassword(ResetEmailDto dto) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        this.producer.send(dto, headers);
    }

    public void processRequestResetPassword(ResetEmailDto dto) {
        //TODO: send reset password email to user
    }
}
