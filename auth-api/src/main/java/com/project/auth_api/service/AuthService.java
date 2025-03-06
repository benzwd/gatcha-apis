package com.project.auth_api.service;

import com.project.auth_api.model.Token;
import com.project.auth_api.model.User;
import com.project.auth_api.exception.UnauthorizedException;
import com.project.auth_api.repository.TokenRepository;
import com.project.auth_api.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private static final int TOKEN_VALIDITY_IN_HOURS = 2 ;

    @PostConstruct
    public void init(){
        if(userRepository.count() == 0) {
            String username = "root";
            String password = "root";
            createUser(username, password);
        }
    }

    /**
     * Authentifie les informations d'un utilisateur et génère un token.
     *
     * @param username nom d'utilisateur.
     * @param password mot de passe.
     * @return token généré.
     */
    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new UnauthorizedException("Nom d'utilisateur ou mot de passe invalide.");
        }
        String tokenStr = generateToken(username);
        LocalDateTime expiration = LocalDateTime.now().plusHours(TOKEN_VALIDITY_IN_HOURS);
        Token token = new Token();
        token.setToken(tokenStr);
        token.setUsername(username);
        token.setExpiration(expiration);
        tokenRepository.save(token);
        return tokenStr;
    }

    /**
     * Valide un token et met à jour sa date d'expiration.
     *
     * @param tokenStr token fourni.
     * @return nom d'utilisateur associé.
     */
    public String validateToken(String tokenStr) {
        Token token = tokenRepository.findByToken(tokenStr);
        if (token == null) {
            throw new UnauthorizedException("Token introuvable.");
        }
        if (token.getExpiration().isBefore(LocalDateTime.now())) {
            throw new UnauthorizedException("Token expiré.");
        }
        // Mise à jour de l'expiration
        token.setExpiration(LocalDateTime.now().plusHours(TOKEN_VALIDITY_IN_HOURS));
        tokenRepository.save(token);
        return token.getUsername();
    }

    /**
     * Génère un token sous la forme username-YYYY/MM/DD-HH:mm:ss.
     *
     * @param username nom d'utilisateur.
     * @return token généré.
     */
    private String generateToken(String username) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss");
        String dateTime = LocalDateTime.now().format(dateFormatter);
        String token = username + "-" + dateTime;
        return token;
    }

    public User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }
}