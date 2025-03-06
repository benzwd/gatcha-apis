package com.example.gatchagame.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO qui représente le résultat d'un combat.
 */
@Getter
@Setter
public class CombatResult {
    private String winnerMonsterId;
    private String winnerPlayerUsername;
    private String combatLogId;
    private List<String> combatLogEntries;

}
