package com.example.gatchagame.controller;

import com.example.gatchagame.dto.CombatResult;
import com.example.gatchagame.model.CombatLog;
import com.example.gatchagame.service.CombatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API REST pour la simulation de combats et la gestion des logs.
 */
@RestController
@RequestMapping("/combat")
public class CombatController {

    @Autowired
    private CombatService combatService;

    /**
     * Simule un combat entre deux monstres.
     *
     * @param monsterId1 identifiant du premier monstre.
     * @param monsterId2 identifiant du second monstre.
     * @return CombatResult.
     */
    @PostMapping
    public ResponseEntity<CombatResult> simulateCombat(@RequestParam String monsterId1,
                                                       @RequestParam String monsterId2) {
        CombatResult result = combatService.simulateCombat(monsterId1, monsterId2);
        return ResponseEntity.ok(result);
    }

    /**
     * Récupère l'historique complet de tous les combats.
     *
     * @param username récupéré via le token.
     * @return liste de CombatLog.
     */
    @GetMapping("/history")
    public ResponseEntity<List<CombatLog>> getCombatHistory(@RequestAttribute("username") String username) {
        List<CombatLog> logs = combatService.getAllCombatLogs();
        return ResponseEntity.ok(logs);
    }

    /**
     * Permet de rediffuser un combat à partir de son identifiant.
     *
     * @param username récupéré via le token.
     * @param combatLogId identifiant du log de combat.
     * @return CombatLog détaillant le combat.
     */
    @GetMapping("/replay/{combatLogId}")
    public ResponseEntity<CombatLog> replayCombat(@RequestAttribute("username") String username,
                                                  @PathVariable String combatLogId) {
        CombatLog log = combatService.getCombatLog(combatLogId);
        return ResponseEntity.ok(log);
    }
}