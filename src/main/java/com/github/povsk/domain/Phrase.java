package com.github.povsk.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "phrase")
public class Phrase {

    @DatabaseField(columnName = "text")
    private String text;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, index = true, columnName = "response_id")
    private Response response;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
