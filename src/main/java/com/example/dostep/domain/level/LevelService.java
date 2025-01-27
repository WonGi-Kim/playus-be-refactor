package com.example.dostep.domain.level;

import com.example.dostep.domain.level.model.Level;
import com.example.dostep.domain.sheet.GoogleSheetHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LevelService {
    private final LevelRepository levelRepository;
    private static final GoogleSheetHelper googleSheetHelper = new GoogleSheetHelper();
    private final MongoTemplate mongoTemplate;

    public void syncLevelExp(String spreadSheetId, String levelExpRange) {
        try {
            // Google Sheets에서 데이터 읽기
            List<List<Object>> levelFData = googleSheetHelper.readSheetData(spreadSheetId, levelExpRange + "!B8:C");
            List<List<Object>> levelBData = googleSheetHelper.readSheetData(spreadSheetId, levelExpRange + "!E8:F");
            List<List<Object>> levelGData = googleSheetHelper.readSheetData(spreadSheetId, levelExpRange + "!H8:I");
            List<List<Object>> levelTData = googleSheetHelper.readSheetData(spreadSheetId, levelExpRange + "!K8:L");

            // 데이터 변환
            List<Level> levelFList = LevelConvert.convertToLevelExp(
                    googleSheetHelper.readCell(spreadSheetId, levelExpRange + "!B7"), levelFData);
            List<Level> levelBList = LevelConvert.convertToLevelExp(
                    googleSheetHelper.readCell(spreadSheetId, levelExpRange + "!E7"), levelBData);
            List<Level> levelGList = LevelConvert.convertToLevelExp(
                    googleSheetHelper.readCell(spreadSheetId, levelExpRange + "!H7"), levelGData);
            List<Level> levelTList = LevelConvert.convertToLevelExp(
                    googleSheetHelper.readCell(spreadSheetId, levelExpRange + "!K7"), levelTData);

            // 기존 데이터 삭제
            mongoTemplate.remove(new Query(), Level.class);

            // MongoTemplate을 사용하여 배치 저장
            mongoTemplate.insert(levelFList, Level.class);
            mongoTemplate.insert(levelBList, Level.class);
            mongoTemplate.insert(levelGList, Level.class);
            mongoTemplate.insert(levelTList, Level.class);

        } catch (Exception e) {
            throw new RuntimeException("MongoDB 동기화 중 오류 발생: " + e.getMessage(), e);
        }
    }

}
