package com.github.povsk.domain;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

public class DaoManager {

    private Dao<GroupBot, Integer> groupDao;
    private Dao<Response, Integer> responseDao;
    private Dao<Phrase, Integer> phraseDao;

    public Dao<GroupBot, Integer> getGroupDao() {
        return groupDao;
    }

    public Dao<Response, Integer> getResponseDao() {
        return responseDao;
    }

    public Dao<Phrase, Integer> getPhraseDao() {
        return phraseDao;
    }

    public static DaoManager configure(ConnectionSource connectionSource) throws Exception {
        DaoManager daoManager = new DaoManager();
        daoManager.groupDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, GroupBot.class);
        daoManager.responseDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Response.class);
        daoManager.phraseDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Phrase.class);
        return daoManager;
    }
}
