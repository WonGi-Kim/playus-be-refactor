package com.example.dostep.domain.quest.group;

import com.example.dostep.domain.quest.group.model.GroupQuest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupQuestRepository extends MongoRepository<GroupQuest, String> {
}
