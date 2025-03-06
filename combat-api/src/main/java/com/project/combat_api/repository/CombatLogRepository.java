package com.example.gatchagame.repository;

import com.example.gatchagame.model.CombatLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour le log des combats.
 */
@Repository
public interface CombatLogRepository extends MongoRepository<CombatLog, String> {
}
