package com.project.monster_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Modèle de base d'un monstre pour l'invocation.
 */
@Getter
@Setter
@Document(collection = "base_monsters")
public class BaseMonster {
    @Id
    private String id;
    private String elementalType;
    private int hp;
    private int atk;
    private int def;
    private int vit;
    private List<Skill> skills;
    private double invocationProbability;
}
