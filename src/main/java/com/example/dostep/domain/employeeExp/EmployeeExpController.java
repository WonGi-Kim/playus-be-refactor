package com.example.dostep.domain.employeeExp;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee/exp")
@RequiredArgsConstructor
public class EmployeeExpController {
    private final EmployeeExpService employeeExpService;

    @Value("${google.spreadsheet.id}")
    private String spreadSheetId; // 스프레드시트 ID

    private static final String EmployeeExpRANGE = "경험치"; //


    @PostMapping("/sync")
    public ResponseEntity<String> syncEmployeeExp() {
        try {
            employeeExpService.syncEmployeeExp(spreadSheetId, EmployeeExpRANGE);
            return ResponseEntity.ok("연도별 경험치 동기화 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("연도별 경험치 동기화 실패 : " + e.getMessage());
        }
    }
}
