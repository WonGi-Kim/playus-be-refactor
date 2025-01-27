package com.example.dostep.domain.evaluation.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "evaluations")
public class Evaluation {
    private String term;  // 평가 기간

    private PersonalEvaluation personalEvaluation; // 개인 평가

    @Builder
    public Evaluation(String term, PersonalEvaluation personalEvaluation) {
        this.term = term;
        this.personalEvaluation = personalEvaluation;
    }
}
