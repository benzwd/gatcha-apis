package com.project.monster_api.model;
import lombok.Getter;
import lombok.Setter;

/**
 * Représente une compétence d'un monstre.
 */
@Getter
@Setter
public class Skill {
    private int baseDamage;
    private double damageRatio; // appliqué à une stat (hp, atk, def ou vit)
    private int cooldown;
    private int currentCooldown; // suivi du cooldown actuel
    private int level;
    private int maxLevel;
}
