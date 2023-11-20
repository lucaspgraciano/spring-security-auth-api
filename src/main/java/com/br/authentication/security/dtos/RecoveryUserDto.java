package com.br.authentication.security.dtos;

public record RecoveryUserDto(
        Long id,
        String name,
        String email,
        String username,
        String role
) {}