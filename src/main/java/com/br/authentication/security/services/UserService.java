package com.br.authentication.security.services;

import com.br.authentication.security.configurations.SecurityConfiguration;
import com.br.authentication.security.dtos.CreateUserDTO;
import com.br.authentication.security.dtos.LoginUserDTO;
import com.br.authentication.security.dtos.RecoveryJWTTokenDTO;
import com.br.authentication.security.dtos.ResetEmailDto;
import com.br.authentication.security.entities.ResetPasswordCode;
import com.br.authentication.security.entities.Role;
import com.br.authentication.security.entities.User;
import com.br.authentication.security.entities.UserDetails;
import com.br.authentication.security.repositories.ResetPasswordCodeRepository;
import com.br.authentication.security.repositories.UserRepository;
import com.br.authentication.ses.services.EmailService;
import com.br.authentication.sqs.producers.SQSMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private SQSMessageProducer producer;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ResetPasswordCodeRepository resetPasswordCodeRepository;

    @Value("${cloud.aws.ses.email}")
    private String emailFrom;

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
        // TODO: verificar se existe usuario com o e-mail no banco
        // TODO: verificar se o código já foi enviado para esse e-mail

        ResetPasswordCode resetPasswordCode = buildCode(dto.email());

        resetPasswordCodeRepository.save(resetPasswordCode);

        SimpleMailMessage simpleMailMessage = buildMailMessage(dto.email(), resetPasswordCode.getCode());

        emailService.sendMessage(simpleMailMessage);
    }

    private ResetPasswordCode buildCode(String email) {
        return ResetPasswordCode.builder()
                .code(generateCode())
                .attempts(0)
                .owner(email)
                .createdAt(new Date())
                .updatedAt(null)
                .verified(Boolean.FALSE)
                .build();
    }

    private String generateCode() {
        return IntStream.range(0, 5)
                .mapToObj(i -> String.valueOf(ThreadLocalRandom.current().nextInt(10)))
                .collect(Collectors.joining());
    }

    private SimpleMailMessage buildMailMessage(String email, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Reset Password Code");
        simpleMailMessage.setText(code);
        return simpleMailMessage;
    }
}
