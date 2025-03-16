package com.project.auth_api.filter;

import com.project.auth_api.exception.UnauthorizedException;
import com.project.auth_api.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    /**
     * Méthode interceptant chaque requête afin de valider le token d'authentification.
     *
     * @param request la requête HTTP entrante.
     * @param response la réponse HTTP.
     * @param filterChain la chaîne de filtres.
     * @throws ServletException en cas d'erreur de servlet.
     * @throws IOException en cas d'erreur d'entrée/sortie.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        // Pas de vérification pour les endpoints d'authentification
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token manquant.");
            return;
        }

        try {
            String username = authService.validateToken(token);
            request.setAttribute("username", username);
            System.out.println("Username extrait : " + username);

            request.setAttribute("token", token);
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }
    }
}