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

    // EmployeeExp
    TOTAL_EXP("2024년 획득한 총 경험치"),
    FIRST_HALF_EVALUATION_EXP("상반기 인사평가"),
    SECOND_HALF_EVALUATION_EXP("하반기 인사평가"),
    GROUP_QUEST_EXP("직무별 퀘스트"),
    LEADER_QUEST_EXP("리더부여 퀘스트"),
    PROJECT_EXP("전사 프로젝트"),

    // Evaluation
    EVL_GRADE("인사평가 등급"),
    EVL_EXPERIENCE("부여 경험치"),
    EVL_NOTE("비고"),

    // Level And Exp
    REQUIRED_EXP("총 필요 경험치"),
    ;

    private final String headerName;

    SheetHeaders(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}
