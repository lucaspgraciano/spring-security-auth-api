package com.br.authentication.security.dtos;

public record CreateUserDTO(
        String name,
        String email,
        String username,
        String password,
        String role
) {}
