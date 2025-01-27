package com.example.dostep.domain.quest.group;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quest/group")
public class GroupQuestController {
    private final GroupQuestService groupQuestService;

    @Value("${google.spreadsheet.id}")
    private String spreadSheetId; // 스프레드시트 ID
    private static final String GroupQuestRange = "직무별 퀘스트"; // 그룹 퀘스트 범위

    @PostMapping("/sync")
    public ResponseEntity<String> syncLevelExp() {
        try {
            groupQuestService.syncGroupQuestData(spreadSheetId, GroupQuestRange);
            return ResponseEntity.ok("레벨 별 경험치 동기화 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("레벨 별 경험치 동기화 실패: " + e.getMessage());
        }
    }
}
