package com.project.Invocation_api.service;

import com.project.Invocation_api.dto.BaseMonsterDTO;
import com.project.Invocation_api.dto.MonsterDTO;
import com.project.Invocation_api.model.InvocationRecord;
import com.project.Invocation_api.repository.InvocationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class InvocationService {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private InvocationRecordRepository invocationRecordRepository;

    private Random random = new Random();

    public MonsterDTO invokeMonster(String username) {
        BaseMonsterDTO baseMonster = selectBaseMonster();
        MonsterDTO monster = prepareMonsterDTO(baseMonster, username);

        MonsterDTO savedMonster = invokeAndSaveMonster(monster);
        addMonsterToPlayer(username, savedMonster);

        // Enregistrement de l'invocation dans la base tampon
        InvocationRecord record = new InvocationRecord();
        record.setUsername(username);
        record.setBaseMonsterId(baseMonster.getId());
        record.setGeneratedMonsterId(savedMonster.getId());
        record.setInvocationTime(LocalDateTime.now());
        invocationRecordRepository.save(record);
        return savedMonster;
    }

    public MonsterDTO invokeAndSaveMonster(MonsterDTO monsterDTO) {
        String url = "http://localhost:8080/monsters/save";
        MonsterDTO savedMonster = restTemplate.postForObject(url, monsterDTO, MonsterDTO.class);
        return savedMonster;
    }

    public void addMonsterToPlayer(String username, MonsterDTO monster) {
        Map<String, String> playerMonsterRequest = new HashMap<>();
        playerMonsterRequest.put("username", username);
        playerMonsterRequest.put("monsterId", monster.getId());

        restTemplate.postForObject("http://localhost:8082/player/monster", playerMonsterRequest, Void.class);
    }

    /**
     * Appelle l'API Monstre pour récupérer la liste des BaseMonsterDTO et en sélectionne un aléatoirement.
     *
     * @return un BaseMonsterDTO sélectionné.
     */
    private BaseMonsterDTO selectBaseMonster() {
        String url = "http://localhost:8082/monsters/base";
        BaseMonsterDTO[] baseMonsters = restTemplate.getForObject(url, BaseMonsterDTO[].class);
        if (baseMonsters == null || baseMonsters.length == 0) {
            throw new RuntimeException("Aucun BaseMonster disponible");
        }
        return baseMonsters[random.nextInt(baseMonsters.length)];
    }

    /**
     * Prépare le MonsterDTO à partir du BaseMonsterDTO et du nom du joueur.
     *
     * @param baseMonster Le BaseMonsterDTO récupéré.
     * @param username    Le nom du joueur.
     * @return Le MonsterDTO préparé.
     */
    private MonsterDTO prepareMonsterDTO(BaseMonsterDTO baseMonster, String username) {
        MonsterDTO monsterDTO = new MonsterDTO();
        monsterDTO.setOwnerUsername(username);
        monsterDTO.setElementalType(baseMonster.getElementalType());
        monsterDTO.setHp(baseMonster.getHp());
        monsterDTO.setAtk(baseMonster.getAtk());
        monsterDTO.setDef(baseMonster.getDef());
        monsterDTO.setVit(baseMonster.getVit());
        monsterDTO.setSkills(baseMonster.getSkills());
        return monsterDTO;
    }
}