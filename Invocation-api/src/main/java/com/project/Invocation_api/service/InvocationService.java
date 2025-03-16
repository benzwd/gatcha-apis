package com.project.Invocation_api.service;

import com.project.Invocation_api.dto.BaseMonsterDTO;
import com.project.Invocation_api.dto.MonsterDTO;
import com.project.Invocation_api.model.InvocationRecord;
import com.project.Invocation_api.repository.InvocationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    public MonsterDTO invokeMonster(String username, String token) {
        System.out.println("USERNAME INVOKE: " + username); // Log les en-têtes
        System.out.println("token INVOKE: " + token); // Log les en-têtes

        BaseMonsterDTO baseMonster = selectBaseMonster(token);
        MonsterDTO monster = prepareMonsterDTO(baseMonster, username);

        MonsterDTO savedMonster = invokeAndSaveMonster(monster, token);
        addMonsterToPlayer(token, username, savedMonster);

        // Enregistrement de l'invocation dans la base tampon
        InvocationRecord record = new InvocationRecord();
        record.setUsername(username);
        record.setBaseMonsterId(baseMonster.getId());
        record.setGeneratedMonsterId(savedMonster.getId());
        record.setInvocationTime(LocalDateTime.now());
        invocationRecordRepository.save(record);
        return savedMonster;
    }

    public MonsterDTO invokeAndSaveMonster(MonsterDTO monsterDTO, String token) {
        String url = "http://monster-api:8082/monsters/save";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<MonsterDTO> entity = new HttpEntity<>(monsterDTO, headers);

        try {
            ResponseEntity<MonsterDTO> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, MonsterDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Échec de la requête : " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log l'exception pour le débogage
            throw new RuntimeException("Erreur lors de la sauvegarde du monstre", e);
        }
    }

    public void addMonsterToPlayer(String token, String username, MonsterDTO monster) {
        String url = "http://player-api:8081/player/monster?monsterId=" + monster.getId();
        Map<String, String> playerMonsterRequest = new HashMap<>();
        playerMonsterRequest.put("username", username);
        playerMonsterRequest.put("monsterId", monster.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(playerMonsterRequest, headers);
        System.out.println("MonsterID: " + monster.getId()); // Log les en-têtes


        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, Void.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Échec de la requête : " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log l'exception pour le débogage
            throw new RuntimeException("Erreur lors de l'ajout du monstre au joueur", e);
        }
    }

    /**
     * Appelle l'API Monstre pour récupérer la liste des BaseMonsterDTO et en sélectionne un aléatoirement.
     *
     * @return un BaseMonsterDTO sélectionné.
     */
    private BaseMonsterDTO selectBaseMonster(String token) {
        String url = "http://monster-api:8082/monsters/base";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token); // Utilisez le token ici
        HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println("En-têtes de la requête : " + headers); // Log les en-têtes

        try {
            ResponseEntity<BaseMonsterDTO[]> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, BaseMonsterDTO[].class);

            if (response.getStatusCode().is2xxSuccessful()) {
                BaseMonsterDTO[] baseMonsters = response.getBody();
                if (baseMonsters == null || baseMonsters.length == 0) {
                    throw new RuntimeException("Aucun BaseMonster disponible");
                }
                return baseMonsters[random.nextInt(baseMonsters.length)];
            } else {
                throw new RuntimeException("Échec de la requête : " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log l'exception pour le débogage
            throw new RuntimeException("Erreur lors de la récupération des BaseMonsters", e);
        }
    }

    /**
     * Prépare le MonsterDTO à partir du BaseMonsterDTO et du nom du joueur.
     *
     * @param baseMonster Le BaseMonsterDTO récupéré.
     * @param username    Le nom du joueur.
     * @return Le MonsterDTO préparé.
     */
    private MonsterDTO prepareMonsterDTO(BaseMonsterDTO baseMonster, String username) {
        System.out.println("USERNAME PREPARE: " + username); // Log les en-têtes

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