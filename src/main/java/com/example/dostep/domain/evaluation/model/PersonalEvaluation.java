package com.example.dostep.domain.evaluation.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PersonalEvaluation {
    private Long employeeId;  // 사번
    private String name;        // 대상자
    private String grade;       // 인사평가 등급
    private int experience;     // 부여 경험치
    private String note;        // 비고

    @Builder
    public PersonalEvaluation(Long employeeId, String name, String grade, int experience, String note) {
        this.employeeId = employeeId;
        this.name = name;
        this.grade = grade;
        this.experience = experience;
        this.note = note;
    }
}
