package com.example.dostep.domain.evaluation;

import com.example.dostep.domain.employee.EmployeeRepository;
import com.example.dostep.domain.evaluation.model.Evaluation;
import com.example.dostep.domain.sheet.GoogleSheetHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private static final GoogleSheetHelper googleSheetHelper = new GoogleSheetHelper();
    private final EmployeeRepository employeeRepository;

    public void syncEvaluation(String spreadSheetId, String evaluationRange) {
        try {
            processEvaluationData(spreadSheetId, evaluationRange, "!B7", "!B9:F");
            processEvaluationData(spreadSheetId, evaluationRange, "!H7", "!H9:L");
        } catch (Exception e) {
            throw new RuntimeException("MongoDB 동기화 중 오류 발생: " + e.getMessage());
        }
    }

    private void processEvaluationData(String spreadSheetId, String evaluationRange, String termCell, String personalEvaluationRange) throws GeneralSecurityException, IOException {// 평가 기간 및 데이터 읽기
        String termRange = evaluationRange + termCell;
        String evaluationDataRange = evaluationRange + personalEvaluationRange;

        String term = googleSheetHelper.readCell(spreadSheetId, termRange);
        List<List<Object>> evaluationData = googleSheetHelper.readSheetData(spreadSheetId, evaluationDataRange);

        // 평가 데이터 변환
        List<Evaluation> evaluationList = EvaluationConvert.convertToEvaluation(term, evaluationData);
        List<Evaluation> existingEvaluationList = evaluationRepository.findAllByTerm(term);

        // 중복 필터링
        List<Evaluation> evaluationToSave = evaluationList.stream()
                .filter(newEvaluation -> existingEvaluationList.stream()
                        .noneMatch(existingEvaluation ->
                                Objects.equals(existingEvaluation.getPersonalEvaluation().getEmployeeId(), newEvaluation.getPersonalEvaluation().getEmployeeId())))
                .toList();

//        for (Evaluation evaluation : evaluationToSave) {
//        String employeeId = String.valueOf(evaluation.getPersonalEvaluation().getEmployeeId());
//
//        // 최근 경험 정보 생성
//        RecentExpDetail recentExpDetail = RecentExpDetail.builder()
//                .date(evaluation.getModifiedAt())
//                .questGroup("인사평가")
//                .questName(evaluation.getTerm())
//                .score(evaluation.getPersonalEvaluation().getExperience())
//                .build();
//
//        // MongoTemplate을 사용하여 최근 경험 정보 리스트 업데이트
//        Query query = new Query(Criteria.where("_id").is(employeeId));
//        Update update = new Update().push("recentExpDetails", recentExpDetail);
//
//        // 업데이트 수행
//        mongoTemplate.updateFirst(query, update, Employee.class);
//    }
        // 저장
        if (!evaluationToSave.isEmpty()) {
            evaluationRepository.saveAll(evaluationToSave);
        } else {
            System.out.println("이미 동기화된 데이터입니다.");
        }
    }
}
