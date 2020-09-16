package com.github.povsk.domain;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "group_bot")
public class GroupBot {

    public static final String FIELD_GROUP_ID = "group_id";

    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;
    @DatabaseField(columnName = "group_id")
    private Integer groupId;
    @DatabaseField(columnName = "token")
    private String token;
    @ForeignCollectionField
    private ForeignCollection<Response> responses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public ForeignCollection<Response> getResponses() {
        return responses;
    }

    public void setResponses(ForeignCollection<Response> responses) {
        this.responses = responses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupBot groupBot = (GroupBot) o;
        return id.equals(groupBot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
