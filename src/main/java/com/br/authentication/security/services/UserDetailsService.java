package com.br.authentication.security.services;

import com.br.authentication.security.repositories.UserRepository;
import com.br.authentication.security.entities.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetails(
                repository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"))
        );
    }
}
