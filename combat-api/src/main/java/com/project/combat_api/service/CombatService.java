package com.example.gatchagame.service;

import com.example.gatchagame.dto.CombatResult;
import com.example.gatchagame.model.CombatLog;
import com.example.gatchagame.repository.CombatLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CombatService {

    @Autowired
    private MonsterService monsterService;

    @Autowired
    private CombatLogRepository combatLogRepository;

    /**
     * Simule un combat entre deux monstres et enregistre le log du combat.
     *
     * @param monsterId1 identifiant du premier monstre.
     * @param monsterId2 identifiant du second monstre.
     * @return CombatResult contenant le résultat et l'identifiant du log.
     */
    public CombatResult simulateCombat(String monsterId1, String monsterId2) {
        Monster monster1 = monsterService.getMonster(monsterId1);
        Monster monster2 = monsterService.getMonster(monsterId2);
        List<String> logEntries = new ArrayList<>();
        int round = 1;
        // Exemple simple de combat en tour par tour (à améliorer)
        while (monster1.getHp() > 0 && monster2.getHp() > 0 && round <= 20) {
            logEntries.add("Round " + round);
            // Attaque du monstre 1 sur le monstre 2
            int damage1 = calculateDamage(monster1, monster2);
            monster2.setHp(Math.max(0, monster2.getHp() - damage1));
            logEntries.add(monster1.getId() + " attaque " + monster2.getId() + " pour " + damage1 + " dégâts.");
            if (monster2.getHp() <= 0) break;
            // Attaque du monstre 2 sur le monstre 1
            int damage2 = calculateDamage(monster2, monster1);
            monster1.setHp(Math.max(0, monster1.getHp() - damage2));
            logEntries.add(monster2.getId() + " attaque " + monster1.getId() + " pour " + damage2 + " dégâts.");
            round++;
        }
        String winnerMonsterId = monster1.getHp() > monster2.getHp() ? monster1.getId() : monster2.getId();
        CombatLog combatLog = new CombatLog();
        combatLog.setCombatTime(LocalDateTime.now());
        combatLog.setLogEntries(logEntries);
        CombatLog savedLog = combatLogRepository.save(combatLog);
        CombatResult result = new CombatResult();
        result.setWinnerMonsterId(winnerMonsterId);
        result.setWinnerPlayerUsername(monster1.getHp() > monster2.getHp() ? monster1.getOwnerUsername() : monster2.getOwnerUsername());
        result.setCombatLogId(savedLog.getId());
        result.setCombatLogEntries(logEntries);
        return result;
    }

    /**
     * Calcule les dégâts d'une attaque simple.
     *
     * @param attacker monstre attaquant.
     * @param defender monstre défenseur.
     * @return dégâts calculés.
     */
    private int calculateDamage(Monster attacker, Monster defender) {
        int damage = attacker.getAtk() - defender.getDef();
        return Math.max(0, damage);
    }

    /**
     * Récupère l'historique de tous les combats enregistrés.
     *
     * @return liste de CombatLog.
     */
    public List<CombatLog> getAllCombatLogs() {
        return combatLogRepository.findAll();
    }

    /**
     * Récupère le log d'un combat spécifique à partir de son identifiant.
     *
     * @param combatLogId identifiant du log de combat.
     * @return CombatLog correspondant.
     */
    public CombatLog getCombatLog(String combatLogId) {
        return combatLogRepository.findById(combatLogId)
                .orElseThrow(() -> new RuntimeException("Log de combat introuvable."));
    }
}