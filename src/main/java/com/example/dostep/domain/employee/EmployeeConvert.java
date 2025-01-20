package com.example.dostep.domain.employee;

import com.example.dostep.domain.employee.model.Account;
import com.example.dostep.domain.employee.model.Employee;
import com.example.dostep.domain.employee.model.PersonalInfo;
import com.example.dostep.domain.sheet.GoogleSheetConvertHelper;
import com.example.dostep.global.headers.SheetHeaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeConvert {

    public static List<Employee> convertToUsers(List<List<Object>> sheetData) {
        List<Employee> employees = new ArrayList<>();

        // 헤더 추출 및 헤더 맵 생성
        List<Object> headers = GoogleSheetConvertHelper.extractHeaders(sheetData);
        if (headers.isEmpty()) {
            return employees; // 빈 데이터 반환
        }
        Map<String, Integer> headerIndexMap = GoogleSheetConvertHelper.createHeaderIndexMap(headers);

        // 데이터 행 처리
        for (int i = 1; i < sheetData.size(); i++) { // 첫 번째 줄은 헤더로 간주
            List<Object> row = sheetData.get(i);

            if (row.isEmpty() || row.stream().allMatch(cell -> cell.toString().isBlank())) {;
                continue;
            }
            while (row.size() < headers.size()) {
                row.add("");  // 문자열 기본값으로 빈 문자열, 숫자는 "0"
            }

            // PersonalInfo 생성 및 설정
            PersonalInfo personalInfo = PersonalInfo.builder()
                    .employeeId(Long.parseLong(row.get(headerIndexMap.get(SheetHeaders.EMPLOYEE_ID.getHeaderName())).toString()))
                    .employeeName(row.get(headerIndexMap.get(SheetHeaders.EMPLOYEE_NAME.getHeaderName())).toString())
                    .joinDate(row.get(headerIndexMap.get(SheetHeaders.JOIN_DATE.getHeaderName())).toString())
                    .affiliation(row.get(headerIndexMap.get(SheetHeaders.AFFILIATION.getHeaderName())).toString())
                    .department(Integer.parseInt(row.get(headerIndexMap.get(SheetHeaders.DEPARTMENT.getHeaderName())).toString()))
                    .level(row.get(headerIndexMap.get(SheetHeaders.LEVEL.getHeaderName())).toString())
                    .build();

            // Account 생성 및 설정
            Account account = Account.builder()
                    .username(row.get(headerIndexMap.get(SheetHeaders.USERNAME.getHeaderName())).toString())
                    .defaultPassword(row.get(headerIndexMap.get(SheetHeaders.DEFAULT_PASSWORD.getHeaderName())).toString())
                    .changedPassword(row.get(headerIndexMap.get(SheetHeaders.CHANGED_PASSWORD.getHeaderName())) == null ? null :
                            row.get(headerIndexMap.get(SheetHeaders.CHANGED_PASSWORD.getHeaderName())).toString())
                    .build();

            // 연도별 포인트 설정
            Map<String, Integer> points = new HashMap<>();
            for (int year = 2023; year >= 2013; year--) {
                String yearKey = year + "년";
                if (headerIndexMap.containsKey(yearKey)) {
                    String pointString = row.get(headerIndexMap.get(yearKey)).toString().replace(",", "").trim();

                    // 빈 값 처리: 빈 값이면 0으로 처리
                    if (pointString.isEmpty()) {
                        pointString = "0"; // 빈 값인 경우 0으로 처리
                    }

                    int point = Integer.parseInt(pointString);
                    points.put(String.valueOf(year), point);
                } else {
                    // 연도 열이 없을 경우 0으로 처리
                    points.put(String.valueOf(year), 0);
                }
            }

            Employee employee = Employee.builder()
                    .personalInfo(personalInfo)
                    .account(account)
                    .expList(points)
                    .build();

            employees.add(employee);
        }
        return employees;
    }
}
