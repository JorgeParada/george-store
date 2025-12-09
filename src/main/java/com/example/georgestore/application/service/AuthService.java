package com.example.georgestore.application.service;

import com.example.georgestore.domain.model.Role;
import com.example.georgestore.domain.repository.UserRepository;
import com.example.georgestore.dto.AuthRequest;
import com.example.georgestore.dto.AuthResponse;
import com.example.georgestore.dto.RegisterRequest;
import com.example.georgestore.infrastructure.persistence.entity.UserEntity;
import com.example.georgestore.infrastructure.persistence.entity.UserTokenEntity;
import com.example.georgestore.infrastructure.persistence.repository.UserTokenRepository;
import com.example.georgestore.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserTokenRepository tokenRepository;

    public AuthResponse register(RegisterRequest req) {

        if (userRepository.existsByUsername(req.getUsername()))
            throw new IllegalArgumentException("Username already taken");

        if (userRepository.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already registered");

        UserEntity user = UserEntity.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .roles(Set.of(Role.ROLE_USER))
                .createdAt(Instant.now())
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest req) {
        UserEntity user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash()))
            throw new IllegalArgumentException("Invalid credentials");

        String token = jwtService.generateToken(user.getUsername());
        

    UserTokenEntity tokenEntity = new UserTokenEntity();
    tokenEntity.setUsername(user.getUsername());
    tokenEntity.setToken(token);

    tokenRepository.save(tokenEntity);

        return new AuthResponse(token);
    }
}
