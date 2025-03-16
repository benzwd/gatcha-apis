package com.project.Invocation_api.controller;

import com.project.Invocation_api.dto.MonsterDTO;
import com.project.Invocation_api.service.InvocationService;
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
     * @param token récupéré via le token.
     * @return monstre invoqué.
     */
    @PostMapping
    public ResponseEntity<MonsterDTO> invokeMonster(@RequestAttribute("username") String token) {
        MonsterDTO monster = invocationService.invokeMonster(token);
        return ResponseEntity.ok(monster);
    }
}
