package com.example.dostep.domain.employee.model;

import com.example.dostep.global.Timestamped;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Document(collection = "employees")
public class Employee extends Timestamped {
    @Id
    private String id;
    private PersonalInfo personalInfo;
    private Account account;
    private Map<String, Integer> expList;

    @Builder
    public Employee(PersonalInfo personalInfo, Account account, Map<String, Integer> expList) {
        this.personalInfo = personalInfo;
        this.account = account;
        this.expList = expList;
    }
}
