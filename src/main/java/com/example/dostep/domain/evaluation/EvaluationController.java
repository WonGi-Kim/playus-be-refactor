package com.example.dostep.domain.evaluation;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/evaluation")
@RestController
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Value("${google.spreadsheet.id}")
    private String spreadSheetId; // 스프레드시트 ID
    private static final String EvaluationRange = "인사평가"; // 평가

    @PostMapping("/sync")
    public ResponseEntity<String> syncEvaluation() {
        try {
            evaluationService.syncEvaluation(spreadSheetId, EvaluationRange);
            return ResponseEntity.ok("인사평가 동기화 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인사평가 동기화 실패 : " + e.getMessage());
        }
    }
}
