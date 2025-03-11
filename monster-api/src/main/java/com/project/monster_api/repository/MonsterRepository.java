package com.project.monster_api.repository;

import com.project.monster_api.model.Monster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonsterRepository extends MongoRepository<Monster, String> {
    List<Monster> findByOwnerUsername(String ownerUsername);
}
