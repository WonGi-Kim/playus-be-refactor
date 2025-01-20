package com.example.dostep.global.headers;

public enum SheetHeaders {
    // personal info
    EMPLOYEE_ID("사번"),
    EMPLOYEE_NAME("이름"),
    JOIN_DATE("입사일"),
    AFFILIATION("소속"),
    DEPARTMENT("직무그룹"),
    LEVEL("레벨"),

    // account
    USERNAME("아이디"),
    DEFAULT_PASSWORD("기본패스워드"),
    CHANGED_PASSWORD("변경패스워드"),
    ;

    private final String headerName;

    SheetHeaders(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}
