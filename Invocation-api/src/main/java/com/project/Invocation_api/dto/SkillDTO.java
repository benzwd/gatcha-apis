package com.project.Invocation_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillDTO {
    private int baseDamage;
    private double damageRatio;
    private int cooldown;
    private int currentCooldown;
    private int level;
    private int maxLevel;
}