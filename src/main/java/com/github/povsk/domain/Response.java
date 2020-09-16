package com.github.povsk.domain;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "response")
public class Response {

    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;
    @DatabaseField(columnName = "text")
    private String text;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "group_bot_id")
    private GroupBot groupBot;
    @ForeignCollectionField
    private ForeignCollection<Phrase> phrases;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GroupBot getGroupBot() {
        return groupBot;
    }

    public void setGroupBot(GroupBot groupBot) {
        this.groupBot = groupBot;
    }

    public ForeignCollection<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(ForeignCollection<Phrase> phrases) {
        this.phrases = phrases;
    }
}
