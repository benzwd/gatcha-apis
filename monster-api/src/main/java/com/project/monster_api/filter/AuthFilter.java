package com.project.monster_api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String AUTH_API_URL = "http://auth-api:8080/auth/validate-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token manquant.");
            return;
        }

        try {
            String username = restTemplate.postForObject(AUTH_API_URL, token, String.class);
            if (username == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalide.");
                return;
            }

            request.setAttribute("username", username);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Échec de l'authentification.");
        }
    }
}
