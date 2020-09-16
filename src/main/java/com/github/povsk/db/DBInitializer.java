package com.github.povsk.db;

import com.github.povsk.AppProperties;
import com.github.povsk.ApplicationContext;
import com.github.povsk.domain.DaoManager;
import com.github.povsk.domain.GroupBot;
import com.github.povsk.domain.Phrase;
import com.github.povsk.domain.Response;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBInitializer {

    private static ApplicationContext context = ApplicationContext.getInstance();

    public static void init() throws SQLException {
        ConnectionSource connectionSource = context.getConnectionSource();
        TableUtils.createTable(connectionSource, GroupBot.class);
        TableUtils.createTable(connectionSource, Response.class);
        TableUtils.createTable(connectionSource, Phrase.class);

        DaoManager daoManager = context.getDaoManager();
        Dao<GroupBot, Integer> groupBotDao = daoManager.getGroupDao();
        Dao<Response, Integer> responseDao = daoManager.getResponseDao();
        Dao<Phrase, Integer> phraseDao = daoManager.getPhraseDao();

        AppProperties properties = context.getProperties();
        GroupBot groupBot = new GroupBot();
        groupBot.setToken(properties.getToken());
        groupBot.setGroupId(properties.getGroupId());
        groupBotDao.create(groupBot);

        Response response = new Response();
        response.setGroupBot(groupBot);
        response.setText("Добро пожаловать в группу");
        responseDao.create(response);

        Phrase phrase = new Phrase();
        phrase.setText("Привет");
        phrase.setResponse(response);
        phraseDao.create(phrase);
        phrase = new Phrase();
        phrase.setText("Добрый");
        phrase.setResponse(response);
        phraseDao.create(phrase);
        phrase = new Phrase();
        phrase.setText("день");
        phrase.setResponse(response);
        phraseDao.create(phrase);
    }
}
