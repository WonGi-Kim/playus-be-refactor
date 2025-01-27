package com.example.dostep.domain.evaluation;

import com.example.dostep.domain.evaluation.model.Evaluation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends MongoRepository<Evaluation, String> {
    List<Evaluation> findAllByTerm(String term);
}
