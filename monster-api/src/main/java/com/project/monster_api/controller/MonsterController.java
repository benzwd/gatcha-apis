package com.project.monster_api.controller;

import com.project.monster_api.model.BaseMonster;
import com.project.monster_api.model.Monster;
import com.project.monster_api.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * API REST pour la gestion des monstres.
 */
@RestController
@RequestMapping("/monsters")
public class MonsterController {

    @Autowired
    private MonsterService monsterService;

    /**
     * Récupère tous les monstres du joueur authentifié.
     *
     * @param username récupéré via le token.
     * @return liste des monstres.
     */
    @GetMapping
    public ResponseEntity<List<Monster>> getPlayerMonsters(@RequestAttribute("username") String username) {
        return ResponseEntity.ok(monsterService.getMonstersForPlayer(username));
    }

    /**
     * Ajoute de l'expérience à un monstre.
     *
     * @param monsterId identifiant du monstre.
     * @param xp points d'expérience.
     * @return monstre mis à jour.
     */
    @PostMapping("/{monsterId}/xp")
    public ResponseEntity<Monster> addExperience(@PathVariable String monsterId,
                                                 @RequestParam double xp) {
        return ResponseEntity.ok(monsterService.addExperience(monsterId, xp));
    }

    @PostMapping("/save")
    public ResponseEntity<Monster> saveMonster(@RequestAttribute("username") String username, @RequestBody Monster monster) {
        monster.setOwnerUsername(username);
        Monster savedMonster = monsterService.saveMonster(monster);
        return ResponseEntity.ok(savedMonster);
    }

    @GetMapping("/base")
    public ResponseEntity<List<BaseMonster>> getAllBaseMonsters(@RequestAttribute("username") String username) {
        List<BaseMonster> baseMonsters = monsterService.getAllBaseMonsters();
        return ResponseEntity.ok(baseMonsters);
    }

    /**
     * Endpoint pour créer un nouveau monstre.
     *
     * @param monsterName Nom du monstre à créer.
     * @return Le monstre créé.
     */
    @PostMapping("/create")
    public ResponseEntity<Monster> createMonster(@RequestBody String monsterName, @RequestBody BaseMonster baseMonster) {
        Monster createdMonster = monsterService.createMonster(monsterName, baseMonster);
        return ResponseEntity.ok(createdMonster);
    }

    /**
     * Récupère un monstre de base à partir de son identifiant.
     *
     * @param baseMonsterId L'identifiant du monstre de base à récupérer.
     * @return Le monstre de base correspondant ou un statut 404 si non trouvé.
     */
    @GetMapping("/monster-base/{baseMonsterId}")
    public ResponseEntity<Optional<BaseMonster>> getBaseMonsterById(@PathVariable String baseMonsterId) {
        Optional<BaseMonster> baseMonster = monsterService.getBaseMonsterById(baseMonsterId);
        return ResponseEntity.ok(baseMonster);
    }
}
