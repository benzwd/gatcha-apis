package com.project.auth_api.repository;

import com.project.auth_api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Recherche un utilisateur par nom d'utilisateur.
     *
     * @param username le nom d'utilisateur à rechercher.
     * @return l'utilisateur correspondant, ou null s'il n'existe pas.
     */
    User findByUsername(String username);
}
