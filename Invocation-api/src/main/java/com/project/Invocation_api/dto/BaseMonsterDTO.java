package com.project.Invocation_api.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class BaseMonsterDTO {
    private String id;
    private String elementalType;
    private int hp;
    private int atk;
    private int def;
    private int vit;
    private List<SkillDTO> skills;
    private double invocationProbability;
}