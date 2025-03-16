package com.project.monster_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Représente un monstre.
 */
@Getter
@Setter
@Document(collection = "monsters")
public class Monster {
    @Id
    private String id;
    private String ownerUsername;
    private String elementalType;
    private int level;
    private int hp;
    private int atk;
    private int def;
    private int vit;
    private double xp;
    private List<Skill> skills;
}
