package com.project.auth_api.controller;

import com.project.auth_api.dto.AuthRequest;
import com.project.auth_api.dto.AuthResponse;
import com.project.auth_api.exception.UnauthorizedException;
import com.project.auth_api.model.User;
import com.project.auth_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Endpoint pour authentifier un utilisateur.
     *
     * @param authRequest objet contenant username et password.
     * @return token généré.
     */
    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        String token = authService.authenticate(authRequest.getUsername(), authRequest.getPassword());
        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestBody String token) {
        try {
            String username = authService.validateToken(token);
            return ResponseEntity.ok(username);
        } catch (UnauthorizedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide.");
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> create(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        User user = authService.createUser(username, password);
        return ResponseEntity.ok(user);
    }
}