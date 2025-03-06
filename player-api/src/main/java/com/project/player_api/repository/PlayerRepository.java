package com.project.player_api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Player.
 */
@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
}
