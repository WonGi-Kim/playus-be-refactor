package com.example.dostep.domain.employeeExp;

import com.example.dostep.domain.employeeExp.model.EmployeeExp;
import com.example.dostep.domain.sheet.GoogleSheetHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeExpService {

    private final MongoTemplate mongoTemplate;

    private final EmployeeExpRepository employeeExpRepository;
    private static final GoogleSheetHelper googleSheetHelper = new GoogleSheetHelper();

    public void syncEmployeeExp(String spreadSheetId, String employeeExpRange) {
        try {
            for(int year = 2022; year <= 2024; year++) {
                String yearRange = year + " " + employeeExpRange;

                String titleRange = yearRange + "!B23";
                String title = googleSheetHelper.readCell(spreadSheetId, titleRange);

                int maxExp = Integer.parseInt(googleSheetHelper.readCell(spreadSheetId, yearRange + "!C14"))
                        + Integer.parseInt(googleSheetHelper.readCell(spreadSheetId, yearRange + "!E14"))
                        + Integer.parseInt(googleSheetHelper.readCell(spreadSheetId, yearRange + "!G14"));

                String employeeExpDataRange = yearRange + "!B25:L";
                List<List<Object>> groupEmployeeExpData = googleSheetHelper.readSheetData(spreadSheetId, employeeExpDataRange);
                List<EmployeeExp> employeeExpList = EmployeeExpConvert.convertToEmployeeExp(title, groupEmployeeExpData);

                for(EmployeeExp employeeExp : employeeExpList) {
                    employeeExp.setYear(year);
                    employeeExp.setMaxExp(maxExp);
                }

                employeeExpRepository.deleteByYear(year);

                // MongoTemplate을 이용해 한 번에 삽입 (saveAll에 비해 성능 개선)
                mongoTemplate.insertAll(employeeExpList);

//                // MongoDB BulkWrite를 사용하여 한 번에 저장 (대량 데이터 처리 최적화)
//                List<WriteModel<EmployeeExp>> bulkOperations = employeeExpList.stream()
//                        .map(empExp -> new InsertOneModel<>(empExp))
//                        .collect(Collectors.toList());
//
//                BulkWriteResult result = mongoTemplate.getCollection("employeeExp")
//                        .bulkWrite(bulkOperations);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while syncing Google Sheets to MongoDB: " + e.getMessage());
        }
    }
}
