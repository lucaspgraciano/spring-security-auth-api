package com.br.authentication.dtos;

public record CreateUserDTO(
        String name,
        String email,
        String username,
        String password,
        String role
) {}
