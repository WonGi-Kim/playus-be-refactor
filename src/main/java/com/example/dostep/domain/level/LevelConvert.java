package com.example.dostep.domain.level;

import com.example.dostep.domain.level.model.Level;
import com.example.dostep.domain.level.model.LevelExp;
import com.example.dostep.global.headers.SheetHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.dostep.domain.sheet.GoogleSheetConvertHelper.createHeaderIndexMap;
import static com.example.dostep.domain.sheet.GoogleSheetConvertHelper.extractHeaders;

public class LevelConvert {
    public static List<Level> convertToLevelExp(String levelGroup, List<List<Object>> levelData) {
        List<Level> levels = new ArrayList<>();
        List<LevelExp> levelExps = new ArrayList<>();

        Map<String, Integer> levelHeaderIndexMap = createHeaderIndexMap(extractHeaders(levelData));

        for (int i = 1; i < levelData.size(); i++) {
            List<Object> levelRow = levelData.get(i);
            LevelExp levelExp = LevelExp.builder()
                    .level(levelRow.get(levelHeaderIndexMap.get(SheetHeaders.LEVEL.getHeaderName())) == null
                            ? "" : levelRow.get(levelHeaderIndexMap.get(SheetHeaders.LEVEL.getHeaderName())).toString())
                    .exp(levelHeaderIndexMap.get(SheetHeaders.REQUIRED_EXP.getHeaderName()) != null
                            && !levelRow.get(levelHeaderIndexMap.get(SheetHeaders.REQUIRED_EXP.getHeaderName())).toString().isEmpty()
                            ? Integer.parseInt(levelRow.get(levelHeaderIndexMap.get(SheetHeaders.REQUIRED_EXP.getHeaderName())).toString()) : 0)
                    .build();
            levelExps.add(levelExp);
        }
        Level level = Level.builder()
                .levelGroup(levelGroup)
                .levelExp(levelExps)
                .build();
        levels.add(level);

        return levels;
    }
}
