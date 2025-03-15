package com.project.monster_api.repository;

import com.project.monster_api.model.BaseMonster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface BaseMonsterRepository extends MongoRepository<BaseMonster, String> {
    List<BaseMonster> findAll();

    @Query("{ '_id': ?0 }")
    Optional<BaseMonster> findBaseMonsterById(String id);
}
