package com.project.Invocation_api.repository;

import com.project.Invocation_api.model.InvocationRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour les enregistrements d'invocation.
 */
@Repository
public interface InvocationRecordRepository extends MongoRepository<InvocationRecord, String> {
}
