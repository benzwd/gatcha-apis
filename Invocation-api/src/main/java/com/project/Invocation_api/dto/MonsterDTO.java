package com.project.Invocation_api.dto;
import org.springframework.data.annotation.Id;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Représente un monstre.
 */
@Getter
@Setter
public class MonsterDTO {
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
    private List<SkillDTO> skills;
}

