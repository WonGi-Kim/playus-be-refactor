package com.example.dostep.domain.quest.group;

import com.example.dostep.domain.quest.group.model.GroupExperience;
import com.example.dostep.domain.quest.group.model.GroupQuest;
import com.example.dostep.domain.sheet.GoogleSheetHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupQuestService {
    private final GroupQuestRepository groupQuestRepository;
    private static final GoogleSheetHelper googleSheetHelper = new GoogleSheetHelper();
    private final MongoTemplate mongoTemplate;


    public void syncGroupQuestData(String spreadSheetId, String groupQuestRange) throws Exception {
        List<GroupQuest> newGroupQuests = moduleGroupQuestData(spreadSheetId, groupQuestRange);

        if (newGroupQuests.isEmpty()) {
            return;
        }

        String affiliation = newGroupQuests.get(0).getAffiliation();
        int department = newGroupQuests.get(0).getDepartment();

        // 기존 데이터 조회
        Query existingQuery = new Query();
        existingQuery.addCriteria(Criteria.where("affiliation").is(affiliation)
                .and("department").is(department));
        List<GroupQuest> existingGroupQuests = mongoTemplate.find(existingQuery, GroupQuest.class);

        // 새로운 데이터 업데이트 또는 추가
        for (GroupQuest newQuest : newGroupQuests) {
            boolean isUpdated = false;

            for (GroupQuest existingQuest : existingGroupQuests) {
                GroupExperience existingExp = existingQuest.getGroupExperiences();
                GroupExperience newExp = newQuest.getGroupExperiences();

                if (existingExp.getWeek() == newExp.getWeek()) {
                    if (existingExp.isDifferent(newExp)) {
                        // 기존 경험치 업데이트
                        Update update = new Update();
                        update.set("groupExperiences.experience", newExp.getExperience());
                        update.set("groupExperiences.etc", newExp.getEtc());
                        update.set("modifiedAt", new Date());

                        Query updateQuery = new Query(Criteria.where("_id").is(existingQuest.getId()));
                        mongoTemplate.updateFirst(updateQuery, update, GroupQuest.class);

                        // 최근 경험 정보 업데이트
//                        RecentExpDetail recentExpDetail = RecentExpDetail.builder()
//                                .date(new Date())
//                                .questGroup("그룹 퀘스트")
//                                .questName("직무별 퀘스트")
//                                .score(newExp.getExperience())
//                                .build();

                        // 직원들의 recentExpDetails 업데이트
                        Query employeeQuery = new Query(Criteria.where("personalInfo.department").is(affiliation));
//                        Update employeeUpdate = new Update().push("recentExpDetails", recentExpDetail);
//                        mongoTemplate.updateMulti(employeeQuery, employeeUpdate, Employee.class);
                    }
                    isUpdated = true;
                    break;
                }
            }

            if (!isUpdated) {
                mongoTemplate.insert(newQuest);
            }
        }
    }

    private List<GroupQuest> moduleGroupQuestData(String spreadSheetId, String groupQuestRange) throws Exception {
        String groupRange = groupQuestRange + "!F10:H11";
        String expPerWeekRange = groupQuestRange + "!B13:D";
        String scoreInfo = groupQuestRange + "!B10:C11";

        List<List<Object>> groupData = googleSheetHelper.readSheetData(spreadSheetId, groupRange);
        List<List<Object>> expPerWeekData = googleSheetHelper.readSheetData(spreadSheetId, expPerWeekRange);
        List<List<Object>> scoreData = googleSheetHelper.readSheetData(spreadSheetId, scoreInfo);

        List<GroupQuest> groupQuestList = GroupQuestConvert.convertToGroupQuest(groupData, expPerWeekData, scoreData);
        return groupQuestList;
    }
}
