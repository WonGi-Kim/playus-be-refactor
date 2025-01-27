package com.example.dostep.domain.evaluation;

import com.example.dostep.domain.evaluation.model.Evaluation;
import com.example.dostep.domain.evaluation.model.PersonalEvaluation;
import com.example.dostep.global.headers.SheetHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.dostep.domain.sheet.GoogleSheetConvertHelper.createHeaderIndexMap;
import static com.example.dostep.domain.sheet.GoogleSheetConvertHelper.extractHeaders;

public class EvaluationConvert {
    public static List<Evaluation> convertToEvaluation(String term, List<List<Object>> evaluationData) {
        List<Evaluation> evaluations = new ArrayList<>();

        Map<String, Integer> evaluationHeaderIndexMap = createHeaderIndexMap(extractHeaders(evaluationData));

        for (int i = 1; i < evaluationData.size(); i++) {
            List<Object> evaluationRow = evaluationData.get(i);

            PersonalEvaluation personalEvaluation = PersonalEvaluation.builder()
                    .employeeId(evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EMPLOYEE_ID.getHeaderName())) != null
                            && !evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EMPLOYEE_ID.getHeaderName())).toString().isEmpty()
                            ? Long.parseLong(evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EMPLOYEE_ID.getHeaderName())).toString()) : 0)
                    .name(evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EMPLOYEE_NAME.getHeaderName())) == null
                            ? "" : evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EMPLOYEE_NAME.getHeaderName())).toString())
                    .grade(evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EVL_GRADE.getHeaderName())) == null
                            ? "" : evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EVL_GRADE.getHeaderName())).toString())
                    .experience(evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EVL_EXPERIENCE.getHeaderName())) != null
                            && !evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EVL_EXPERIENCE.getHeaderName())).toString().isEmpty()
                            ? Integer.parseInt(evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EVL_EXPERIENCE.getHeaderName())).toString()) : 0)
                    .note(evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EVL_NOTE.getHeaderName())) == null
                            ? "-" : evaluationRow.get(evaluationHeaderIndexMap.get(SheetHeaders.EVL_NOTE.getHeaderName())).toString())
                    .build();

            Evaluation evaluation = Evaluation.builder()
                    .term(term)
                    .personalEvaluation(personalEvaluation)
                    .build();
            evaluations.add(evaluation);
        }
        return evaluations;
    }
}
