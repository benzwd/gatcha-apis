package com.project.player_api.repository;

import com.project.player_api.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Player.
 */
@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
}
