package com.project.monster_api.repository;

import com.project.monster_api.model.BaseMonster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseMonsterRepository extends MongoRepository<BaseMonster, String> {
    List<BaseMonster> findAll();
}
