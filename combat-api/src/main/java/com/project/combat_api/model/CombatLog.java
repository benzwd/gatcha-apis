package com.example.gatchagame.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Enregistre le log d'un combat.
 */
@Getter
@Setter
@Document(collection = "combat_logs")
public class CombatLog {
    @Id
    private String id;
    private List<String> logEntries;
    private LocalDateTime combatTime;
}
