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
        Response response1 = new Response();
        response1.setGroupBot(groupBot);
        response1.setText("Я чувствую себя живым!");
        Response response2 = new Response();
        responseDao.create(response1);
        response2.setGroupBot(groupBot);
        response2.setText("Хочу пригасить Вас на виртуальную чашку кофе");
        responseDao.create(response2);
        Response response3 = new Response();
        response3.setGroupBot(groupBot);
        response3.setText("У меня есть одно очень занимательное видео: https://www.youtube.com/watch?v=SW_UCzFO7X0&list=PLawfWYMUziZqyUL5QDLVbe3j5BKWj42E5&index=2");
        responseDao.create(response3);
        Response response4 = new Response();
        response4.setGroupBot(groupBot);
        response4.setText("Самое время поесть и поиграть в теннис на первом этаже");
        responseDao.create(response4);
        Response response5 = new Response();
        response5.setGroupBot(groupBot);
        response5.setText("Девочка покорившая время");
        responseDao.create(response5);

        Phrase phrase = new Phrase();
        phrase.setText("Привет");
        phrase.setResponse(response);
        phraseDao.create(phrase);
        Phrase phrase = new Phrase();
        phrase.setText("Добрый");
        phrase.setResponse(response);
        phraseDao.create(phrase);
        Phrase phrase = new Phrase();
        phrase.setText("день");
        phrase.setResponse(response);
        phraseDao.create(phrase);
        Phrase phrase1 = new Phrase();
        phrase1.setText("чувствуешь");
        phrase1.setResponse(response1);
        phraseDao.create(phrase1);
        Phrase phrase2 = new Phrase();
        phrase2.setText("кофе");
        phrase2.setResponse(response2);
        phraseDao.create(phrase2);
        Phrase phrase3 = new Phrase();
        phrase3.setText("скука");
        phrase.setResponse(response3);
        phraseDao.create(phrase3);
        Phrase phrase4 = new Phrase();
        phrase4.setText("час");
        phrase4.setResponse(response4);
        phraseDao.create(phrase4);
        Phrase phrase5 = new Phrase();
        phrase5.setText("фильм");
        phrase5.setResponse(response5);
        phraseDao.create(phrase5);
    }
}
