package com.project.Invocation_api.service;

import com.example.gatchagame.model.InvocationRecord;
import com.example.gatchagame.repository.BaseMonsterRepository;
import com.example.gatchagame.repository.InvocationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Service qui gère l'invocation des monstres.
 */
@Service
public class InvocationService {

    @Autowired
    private BaseMonsterRepository baseMonsterRepository;

    @Autowired
    private MonsterService monsterService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private InvocationRecordRepository invocationRecordRepository;

    private Random random = new Random();

    /**
     * Effectue une invocation pour le joueur.
     *
     * @param username nom du joueur.
     * @return monstre invoqué.
     */
    public Monster invokeMonster(String username) {
        BaseMonster baseMonster = selectBaseMonster();
        // Création d'un nouveau monstre à partir du template de base
        Monster monster = new Monster();
        monster.setOwnerUsername(username);
        monster.setElementalType(baseMonster.getElementalType());
        monster.setHp(baseMonster.getBaseHp());
        monster.setAtk(baseMonster.getBaseAtk());
        monster.setDef(baseMonster.getBaseDef());
        monster.setVit(baseMonster.getBaseVit());
        monster.setLevel(1);
        monster.setXp(0);
        monster.setSkills(baseMonster.getBaseSkills());
        Monster savedMonster = monsterService.saveMonster(monster);
        // Mise à jour du profil joueur
        playerService.addMonster(username, savedMonster.getId());
        // Enregistrement de l'invocation
        InvocationRecord record = new InvocationRecord();
        record.setUsername(username);
        record.setBaseMonsterId(baseMonster.getId());
        record.setGeneratedMonsterId(savedMonster.getId());
        record.setInvocationTime(LocalDateTime.now());
        invocationRecordRepository.save(record);
        return savedMonster;
    }

    /**
     * Sélectionne aléatoirement un BaseMonster en fonction de ses probabilités.
     *
     * @return BaseMonster sélectionné.
     */
    private BaseMonster selectBaseMonster() {
        List<BaseMonster> baseMonsters = baseMonsterRepository.findAll();
        double randomValue = random.nextDouble() * 100;
        double cumulativeProbability = 0.0;
        for (BaseMonster baseMonster : baseMonsters) {
            cumulativeProbability += baseMonster.getInvocationProbability();
            if (randomValue <= cumulativeProbability) {
                return baseMonster;
            }
        }
        return baseMonsters.get(baseMonsters.size() - 1);
    }
}
