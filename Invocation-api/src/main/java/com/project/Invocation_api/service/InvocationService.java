    package com.project.Invocation_api.service;

    import com.project.Invocation_api.model.InvocationRecord;
    import com.project.monster_api.model.BaseMonster;
    import com.project.monster_api.model.Monster;
    import com.project.monster_api.repository.BaseMonsterRepository;
    import com.project.Invocation_api.repository.InvocationRecordRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Random;

    /**
     * Service qui gère l'invocation des monstres.
     */
    @Service
    public class InvocationService {

        private RestTemplate restTemplate = new RestTemplate();

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
            Monster savedMonster = invokeAndSaveMonster(monster);
            // Mise à jour du profil joueur
            addMonsterToPlayer(username, savedMonster);
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
            ResponseEntity<BaseMonster[]> response = restTemplate.getForEntity(
                    "http://localhost:8080/monsters/base",
                    BaseMonster[].class
            );
            BaseMonster[] baseMonsters = response.getBody();
            double totalProb = 0;
            for (BaseMonster bm : baseMonsters) {
                totalProb += bm.getInvocationProbability();
            }
            double rand = Math.random() * totalProb;
            BaseMonster chosenBase = null;
            for (BaseMonster bm : baseMonsters) {
                rand -= bm.getInvocationProbability();
                if (rand <= 0) {
                    chosenBase = bm;
                    break;
                }
            }
            if (chosenBase == null && baseMonsters.length > 0) {
                // Au cas où des erreurs d'arrondi se produisent, on prend le dernier de la liste par défaut
                chosenBase = baseMonsters[baseMonsters.length - 1];
            }
            return chosenBase;
        }

        public Monster invokeAndSaveMonster( Monster monster) {
            String url = "http://localhost:8080/monsters/save";
            Monster savedMonster = restTemplate.postForObject(url, monster, Monster.class);
            return savedMonster;
        }

        public void addMonsterToPlayer(String username, Monster monster){
            String url = "http://localhost:8080/players/add";

            Map<String, String> playerMonsterRequest = new HashMap<>();
            playerMonsterRequest.put("username", username);
            playerMonsterRequest.put("monsterId", monster.getId());

            restTemplate.postForObject(
                    "http://localhost:8080/player/monster",
                    playerMonsterRequest,
                    Void.class
            );
        }
    }
