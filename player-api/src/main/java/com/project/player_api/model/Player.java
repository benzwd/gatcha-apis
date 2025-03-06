package com.project.player_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Représente le profil d'un joueur.
 */
@Getter
@Setter
@Document(collection = "players")
public class Player {
    @Id
    private String username;
    private int level;
    private double experience;
    private double experienceThreshold;
    private List<String> monsterIds = new ArrayList<>();

    // Constructeurs, getters et setters
}
