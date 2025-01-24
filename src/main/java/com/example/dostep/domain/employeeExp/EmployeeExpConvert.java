package com.example.dostep.domain.employeeExp;

import com.example.dostep.domain.employeeExp.model.EmployeeExp;
import com.example.dostep.domain.employeeExp.model.ExpForYear;
import com.example.dostep.global.headers.SheetHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.dostep.domain.sheet.GoogleSheetConvertHelper.createHeaderIndexMap;
import static com.example.dostep.domain.sheet.GoogleSheetConvertHelper.extractHeaders;

public class EmployeeExpConvert {

    public static List<EmployeeExp> convertToEmployeeExp(String title, List<List<Object>> groupEmployeeExpData) {

        List<EmployeeExp> employeeExps = new ArrayList<>();

        Map<String, Integer> groupEmployeeExpHeaderIndexMap = createHeaderIndexMap(extractHeaders(groupEmployeeExpData));

        for (int i = 1; i < groupEmployeeExpData.size(); i++) {
            List<Object> groupEmployeeExpRow = groupEmployeeExpData.get(i);
            ExpForYear expForYear = ExpForYear.builder()
                    .totalExp(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.TOTAL_EXP.getHeaderName())) != null && !groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.TOTAL_EXP.getHeaderName())).toString().isEmpty()
                            ? Integer.parseInt(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.TOTAL_EXP.getHeaderName())).toString()) : 0)
                    .firstHalfEvaluationExp(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.FIRST_HALF_EVALUATION_EXP.getHeaderName())) != null && !groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.FIRST_HALF_EVALUATION_EXP.getHeaderName())).toString().isEmpty()
                            ? Integer.parseInt(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.FIRST_HALF_EVALUATION_EXP.getHeaderName())).toString()) : 0)
                    .secondHalfEvaluationExp(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.SECOND_HALF_EVALUATION_EXP.getHeaderName())) != null && !groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.SECOND_HALF_EVALUATION_EXP.getHeaderName())).toString().isEmpty()
                            ? Integer.parseInt(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.SECOND_HALF_EVALUATION_EXP.getHeaderName())).toString()) : 0)
                    .groupQuestExp(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_EXP.getHeaderName())) != null && !groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_EXP.getHeaderName())).toString().isEmpty()
                            ? Integer.parseInt(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_EXP.getHeaderName())).toString()) : 0)
                    .leaderQuestExp(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.LEADER_QUEST_EXP.getHeaderName())) != null && !groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.LEADER_QUEST_EXP.getHeaderName())).toString().isEmpty()
                            ? Integer.parseInt(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.LEADER_QUEST_EXP.getHeaderName())).toString()) : 0)
                    .projectExp(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.PROJECT_EXP.getHeaderName())) != null && !groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.PROJECT_EXP.getHeaderName())).toString().isEmpty()
                            ? Integer.parseInt(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.PROJECT_EXP.getHeaderName())).toString()) : 0)
                    .build();

            EmployeeExp employeeExp = EmployeeExp.builder()
                    .title(title)
                    .employeeId(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.EMPLOYEE_ID.getHeaderName())) != null && !groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.EMPLOYEE_ID.getHeaderName())).toString().isEmpty()
                            ? Long.parseLong(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.EMPLOYEE_ID.getHeaderName())).toString()) : 0)
                    .name(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.EMPLOYEE_NAME.getHeaderName())) == null ? "" : groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.EMPLOYEE_NAME.getHeaderName())).toString())
                    .affiliation(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.AFFILIATION.getHeaderName())) == null ? "" : groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.AFFILIATION.getHeaderName())).toString())
                    .department(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.DEPARTMENT.getHeaderName())) != null && !groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.DEPARTMENT.getHeaderName())).toString().isEmpty()
                            ? Integer.parseInt(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.DEPARTMENT.getHeaderName())).toString()) : 0)
                    .level(groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.LEVEL.getHeaderName())) == null ? "" : groupEmployeeExpRow.get(groupEmployeeExpHeaderIndexMap.get(SheetHeaders.LEVEL.getHeaderName())).toString())
                    .expForYear(expForYear)
                    .build();
            employeeExps.add(employeeExp);
        }
        return employeeExps;
    }

}
