package com.br.authentication.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.br.authentication.security.entities.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {
    @Value("${auth.jwt.secretKey}")
    private String secretKey;

    @Value("${auth.jwt.issuer}")
    private String issuer;

    @Value("${auth.jwt.expiration}")
    private Integer expiration;

    public String generateToken(UserDetails user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            return JWT.create()
                    .withIssuer(this.issuer)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error generating token", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            return JWT.require(algorithm)
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant().plusMillis(this.expiration);
    }
}
