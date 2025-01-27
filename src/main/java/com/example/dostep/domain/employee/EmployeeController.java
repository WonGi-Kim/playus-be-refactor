package com.example.dostep.domain.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Value("${google.spreadsheet.id}")
    private String spreadSheetId; // 스프레드시트 ID
    private static final String EmployeeRANGE = "구성원정보!B9:V"; // 구성원 정보

    @PostMapping("/sync")
    public ResponseEntity<String> syncEmployees() {
        try {
            employeeService.syncEmployeeData(spreadSheetId, EmployeeRANGE);
            return ResponseEntity.ok("직원 데이터 동기화 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("직원 데이터 동기화 실패: " + e.getMessage());
        }
    }

}
