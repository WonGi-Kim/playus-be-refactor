package com.example.dostep.domain.employee.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // TODO : Setter 추후 제거
public class Account {
    private String username;
    private String defaultPassword;
    private String changedPassword;

    @Builder
    public Account(String username, String defaultPassword, String changedPassword) {
        this.username = username;
        this.defaultPassword = defaultPassword;
        this.changedPassword = changedPassword;
    }
}
