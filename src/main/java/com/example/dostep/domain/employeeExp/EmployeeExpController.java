package com.example.dostep.domain.employeeExp;


import com.example.dostep.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/employee/exp")
@RequiredArgsConstructor
public class EmployeeExpController {
    private final EmployeeExpService employeeExpService;

    @PostMapping("/sync")
    public ResponseEntity<CommonResponse> syncEmployeeExp() {
        try {
            employeeExpService.syncEmployeeExp();
            return ResponseEntity.ok(CommonResponse.success());
        } catch (Exception e) {
            return ResponseEntity.ok(CommonResponse.fail(e.getMessage()));
        }
    }
}
