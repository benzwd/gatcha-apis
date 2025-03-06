package com.project.auth_api.repository;

import com.project.auth_api.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    /**
     * Recherche un token par sa valeur.
     *
     * @param token la valeur du token.
     * @return l'objet Token correspondant, ou null s'il n'existe pas.
     */
    Token findByToken(String token);
}
