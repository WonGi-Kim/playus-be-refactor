package com.example.dostep.global;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Document
public abstract class Timestamped {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @CreatedDate
    @Field("created_at")
    private String createdAt;

    @LastModifiedDate
    @Field("modified_at")
    private String modifiedAt;

    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(FORMATTER);
        }
    }

    public void onUpdate() {
        modifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(FORMATTER);
    }
}

@Component
class TimestampedEntityCallback implements BeforeConvertCallback<Timestamped>, BeforeSaveCallback<Timestamped> {

    @Override
    public Timestamped onBeforeConvert(Timestamped entity, String collection) {
        entity.onCreate();
        return entity;
    }

    @Override
    public Timestamped onBeforeSave(Timestamped entity, org.bson.Document document, String collection) {
        entity.onUpdate();
        return entity;
    }
}