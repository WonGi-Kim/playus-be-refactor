package com.example.dostep.domain.employee.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // TODO : Setter 추후 제거
public class PersonalInfo {
    private Long employeeId;
    private String employeeName;
    private String joinDate;
    private String affiliation;
    private int department;
    private String level;

    @Builder
    public PersonalInfo(Long employeeId, String employeeName, String joinDate, String affiliation, int department, String level) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.joinDate = joinDate;
        this.affiliation = affiliation;
        this.department = department;
        this.level = level;
    }
}
