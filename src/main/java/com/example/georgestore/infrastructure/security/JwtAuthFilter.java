package com.example.georgestore.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.georgestore.infrastructure.persistence.repository.UserTokenRepository;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserTokenRepository tokenRepository;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            SecurityContextHolder.clearContext();
            chain.doFilter(req, res);
            return;
        }

        String token = authHeader.substring(7);

        try {
            if (!jwtService.validate(token)) {
                sendJsonError(res, "Invalid token", "The provided token is invalid or malformed.");
                return;
            }

        } catch (ExpiredJwtException e) {
            sendJsonError(res, "Token expired",
                    "Your session has expired. Please log in again to obtain a new token.");
            return;
        } catch (Exception e) {
            sendJsonError(res, "Invalid token",
                    "The provided token is invalid or malformed.");
            return;
        }

        String username = jwtService.extractUsername(token);

        var lastToken = tokenRepository.findById(username);

        if (lastToken.isEmpty() || !lastToken.get().getToken().equals(token)) {
            sendJsonError(res, "Token revoked",
                    "This token is no longer valid. Please log in again.");
            return;
        }
        
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(username, token, Collections.emptyList());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(req, res);
    }

    private void sendJsonError(HttpServletResponse res, String error, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");

        String body = String.format("""
            {
              "error": "%s",
              "message": "%s"
            }
        """, error, message);

        res.getWriter().write(body);
    }
}
