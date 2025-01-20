package com.example.dostep.domain.sheet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleSheetConvertHelper {

    // SheetData Empty 확인 및 헤더 추출
    public static List<Object> extractHeaders(List<List<Object>> sheetData) {
        if (sheetData.isEmpty()) {
            return Collections.emptyList();
        }
        return sheetData.get(0); // 첫 번째 줄 반환
    }

    // 헤더 맵 생성
    public static Map<String, Integer> createHeaderIndexMap(List<Object> headers) {
        Map<String, Integer> headerIndexMap = new HashMap<>();
        for (int i = 0; i < headers.size(); i++) {
            headerIndexMap.put(headers.get(i).toString(), i);
        }
        return headerIndexMap;
    }
}
