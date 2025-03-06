package com.project.Invocation_api.controller;

import com.example.gatchagame.service.InvocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API REST pour l'invocation des monstres.
 */
@RestController
@RequestMapping("/invocation")
public class InvocationController {

    @Autowired
    private InvocationService invocationService;

    /**
     * Effectue une invocation pour le joueur authentifié.
     *
     * @param username récupéré via le token.
     * @return monstre invoqué.
     */
    @PostMapping
    public ResponseEntity<Monster> invokeMonster(@RequestAttribute("username") String username) {
        Monster monster = invocationService.invokeMonster(username);
        return ResponseEntity.ok(monster);
    }
}
