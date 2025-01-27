package com.example.dostep.domain.quest.group;

import com.example.dostep.domain.quest.group.model.GroupExperience;
import com.example.dostep.domain.quest.group.model.GroupQuest;
import com.example.dostep.global.headers.SheetHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.dostep.domain.sheet.GoogleSheetConvertHelper.createHeaderIndexMap;
import static com.example.dostep.domain.sheet.GoogleSheetConvertHelper.extractHeaders;

public class GroupQuestConvert {
    public static List<GroupQuest> convertToGroupQuest(List<List<Object>> groupData, List<List<Object>> expPerWeekData, List<List<Object>> scoreData) {
        List<GroupQuest> groupQuests = new ArrayList<>();

        Map<String, Integer> groupHeaderIndexMap = createHeaderIndexMap(extractHeaders(groupData));
        Map<String, Integer> expPerWeekHeaderIndexMap = createHeaderIndexMap(extractHeaders(expPerWeekData));
        Map<String, Integer> scoreHeaderIndexMap = createHeaderIndexMap(extractHeaders(scoreData));

        // 하드코딩이 가능한 이유 : 그룹에 따라 별개의 SpreadSheet 를 사용하고 있기 때문에 특정 값이 존재한다는 것을 알고 있음
        String affiliation = groupData.get(1).get(groupHeaderIndexMap.get(SheetHeaders.AFFILIATION.getHeaderName())).toString();
        int department = Integer.parseInt(groupData.get(1).get(groupHeaderIndexMap.get(SheetHeaders.DEPARTMENT.getHeaderName())).toString());
        String period = groupData.get(1).get(groupHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_PERIOD.getHeaderName())).toString();
        int maxScore = Integer.parseInt(scoreData.get(1).get(scoreHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_MAX_SCORE.getHeaderName())).toString());
        int mediumScore = Integer.parseInt(scoreData.get(1).get(scoreHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_MEDIUM_SCORE.getHeaderName())).toString());

        for (int i = 1; i < expPerWeekData.size(); i++) {
            List<Object> expRow = expPerWeekData.get(i);
            GroupExperience experience = GroupExperience.builder()
                    .week(Integer.parseInt(expRow.get(expPerWeekHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_WEEK.getHeaderName())).toString()))
                    .experience(expRow.get(expPerWeekHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_EXPERIENCE.getHeaderName())) != null
                            ? Integer.parseInt(expRow.get(expPerWeekHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_EXPERIENCE.getHeaderName())).toString()) : 0)
                    .etc(expRow.get(expPerWeekHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_ETC.getHeaderName())) == null
                            ? "" : expRow.get(expPerWeekHeaderIndexMap.get(SheetHeaders.GROUP_QUEST_ETC.getHeaderName())).toString())
                    .build();

            GroupQuest groupQuest = GroupQuest.builder()
                    .affiliation(affiliation)
                    .department(department)
                    .period(period)
                    .maxScore(maxScore)
                    .mediumScore(mediumScore)
                    .groupExperiences(experience)
                    .build();
            groupQuests.add(groupQuest);
        }

        return groupQuests;
    }
}
