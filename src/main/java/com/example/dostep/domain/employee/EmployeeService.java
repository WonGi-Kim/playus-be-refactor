package com.example.dostep.domain.employee;

import com.example.dostep.domain.employee.model.Employee;
import com.example.dostep.domain.sheet.GoogleSheetHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final GoogleSheetHelper googleSheetsHelper = new GoogleSheetHelper();

    public void syncEmployeeData(String spreadSheetId, String employeeRANGE) {
        try {
            List<List<Object>> sheetData = googleSheetsHelper.readSheetData(spreadSheetId, employeeRANGE);

            List<Employee> newEmployees = EmployeeConvert.convertToUsers(sheetData);

            // 기존 데이터 조회
            List<Employee> existingEmployees = employeeRepository.findAll();

            // 중복되지 않는 새로운 데이터 필터링
            List<Employee> employeesToSave = newEmployees.stream()
                    .filter(newEmployee -> existingEmployees.stream()
                            .noneMatch(existingEmployee -> existingEmployee.getPersonalInfo().getEmployeeId().equals(newEmployee.getPersonalInfo().getEmployeeId())))
                    .toList();

            // 새로운 데이터 저장
            if (!employeesToSave.isEmpty()) {
                employeeRepository.saveAll(employeesToSave);
            } else {
                System.out.println("No new data to save");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while syncing Google Sheets to MongoDB: " + e.getMessage());
        }


    }
}
