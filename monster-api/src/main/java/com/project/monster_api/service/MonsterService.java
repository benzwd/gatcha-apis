package com.project.monster_api.service;

import com.project.monster_api.model.BaseMonster;
import com.project.monster_api.model.Monster;
import com.project.monster_api.model.Skill;
import com.project.monster_api.repository.BaseMonsterRepository;
import com.project.monster_api.repository.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import jakarta.annotation.PostConstruct;

/**
 * Service qui gère les monstres.
 */
@Service
public class MonsterService {

    @Autowired
    private MonsterRepository monsterRepository;

    // Injection du repository pour BaseMonster
    @Autowired
    private BaseMonsterRepository baseMonsterRepository;

    /**
     * Insère 10 BaseMonster dans la base de données si la collection est vide.
     *
     * @return la liste des BaseMonster insérés.
     */
    @PostConstruct
    public List<BaseMonster> seedBaseMonsters() {
        List<BaseMonster> seeded = new ArrayList<>();
        // Vérifier si la collection base_monsters est vide
        if (baseMonsterRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                BaseMonster baseMonster = new BaseMonster();

                // Définir le type élémentaire selon un schéma cyclique
                baseMonster.setElementalType(getElementalType(i));

                // Définir des statistiques d'exemple
                baseMonster.setBaseHp(100 + i * 10);
                baseMonster.setBaseAtk(20 + i * 2);
                baseMonster.setBaseDef(10 + i);
                baseMonster.setBaseVit(15 + i);

                // Créer une compétence d'exemple
                Skill skill = new Skill();
                skill.setBaseDamage(30 + i * 5);
                skill.setDamageRatio(1.0 + (i * 0.1));
                skill.setCooldown(3);
                skill.setCurrentCooldown(0);
                skill.setLevel(1);
                skill.setMaxLevel(5);

                // Affecter la compétence au BaseMonster
                baseMonster.setBaseSkills(Collections.singletonList(skill));

                // Définir une probabilité d'invocation (ici 10% pour chaque)
                baseMonster.setInvocationProbability(10.0);

                // Sauvegarder le BaseMonster via le repository dédié
                baseMonsterRepository.save(baseMonster);
                seeded.add(baseMonster);
            }
        }
        return seeded;
    }

    /**
     * Méthode d'assistance pour déterminer le type élémentaire en fonction de l'indice.
     */
    private String getElementalType(int i) {
        int mod = i % 3;
        if (mod == 0) {
            return "feu";
        } else if (mod == 1) {
            return "eau";
        } else {
            return "vent";
        }
    }

    /**
     * Récupère tous les monstres appartenant à un joueur.
     *
     * @param username nom du propriétaire.
     * @return liste des monstres.
     */
    public List<Monster> getMonstersForPlayer(String username) {
        return monsterRepository.findByOwnerUsername(username);
    }

    /**
     * Ajoute de l'expérience à un monstre et le fait monter de niveau si nécessaire.
     *
     * @param monsterId identifiant du monstre.
     * @param xp points d'expérience à ajouter.
     * @return monstre mis à jour.
     */
    public Monster addExperience(String monsterId, double xp) {
        Monster monster = getMonster(monsterId);
        monster.setXp(monster.getXp() + xp);
        // Logique de level up à développer selon vos règles
        return monsterRepository.save(monster);
    }

    /**
     * Récupère un monstre par son identifiant.
     *
     * @param monsterId identifiant.
     * @return Monster.
     */
    public Monster getMonster(String monsterId) {
        Optional<Monster> monster = monsterRepository.findById(monsterId);
        return monster.orElseThrow(() -> new RuntimeException("Monstre introuvable."));
    }

    /**
     * Sauvegarde un nouveau monstre.
     *
     * @param monster monstre à sauvegarder.
     * @return monstre sauvegardé.
     */
    public Monster saveMonster(Monster monster) {
        return monsterRepository.save(monster);
    }
}