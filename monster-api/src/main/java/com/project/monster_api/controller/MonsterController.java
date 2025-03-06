package com.project.monster_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
