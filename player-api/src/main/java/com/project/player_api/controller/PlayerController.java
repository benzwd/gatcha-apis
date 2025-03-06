package com.project.player_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API REST pour la gestion des profils joueurs.
 */
@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    /**
     * Récupère le profil du joueur authentifié.
     *
     * @param username récupéré via le filtre d'authentification.
     * @return profil du joueur.
     */
    @GetMapping("/profile")
    public ResponseEntity<Player> getProfile(@RequestAttribute("username") String username) {
        return ResponseEntity.ok(playerService.getProfile(username));
    }

    /**
     * Ajoute de l'expérience au joueur.
     *
     * @param username récupéré via le token.
     * @param xp quantité d'expérience.
     * @return profil mis à jour.
     */
    @PostMapping("/experience")
    public ResponseEntity<Player> addExperience(@RequestAttribute("username") String username,
                                                @RequestParam double xp) {
        return ResponseEntity.ok(playerService.addExperience(username, xp));
    }

    /**
     * Monte le joueur d'un niveau.
     *
     * @param username récupéré via le token.
     * @return profil mis à jour.
     */
    @PostMapping("/levelup")
    public ResponseEntity<Player> levelUp(@RequestAttribute("username") String username) {
        return ResponseEntity.ok(playerService.levelUp(username));
    }

    /**
     * Ajoute un monstre à la collection du joueur.
     *
     * @param username récupéré via le token.
     * @param monsterId identifiant du monstre.
     * @return profil mis à jour.
     */
    @PostMapping("/monster")
    public ResponseEntity<Player> addMonster(@RequestAttribute("username") String username,
                                             @RequestParam String monsterId) {
        return ResponseEntity.ok(playerService.addMonster(username, monsterId));
    }

    /**
     * Supprime un monstre de la collection du joueur.
     *
     * @param username récupéré via le token.
     * @param monsterId identifiant du monstre.
     * @return profil mis à jour.
     */
    @DeleteMapping("/monster/{monsterId}")
    public ResponseEntity<Player> removeMonster(@RequestAttribute("username") String username,
                                                @PathVariable String monsterId) {
        return ResponseEntity.ok(playerService.removeMonster(username, monsterId));
    }
}
